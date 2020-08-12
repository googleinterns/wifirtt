/*
Copyright 2020 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package userinterface;

import structs.SubelementName;

import java.util.HashMap;

public class ArtMvcController {

    private static final String LCI_HEADER = "010008";
    private static final String LCR_HEADER = "01000b";

    private final ArtMvcView view;   // MVC View
    private final ArtMvcModel model; // MVC Model

    private SubelementName lastVisitedSubelement;

    /**
     * Constructor.
     *
     * @param fv the MVC view
     * @param fm the MVC model
     */
    public ArtMvcController(ArtMvcView fv, ArtMvcModel fm) {
        this.model = fm;
        this.view = fv;

        // Initialize MVC structure
        this.model.setCallback(this);

        // Listeners for the checkboxes determining which subelements are included.
        view.addLciIncludedListener(actionEvent ->
            model.getState().setLciIncluded(view.getLciIncluded()));
        view.addZIncludedListener(actionEvent ->
            model.getState().setZIncluded(view.getZIncluded()));
        view.addUsageIncludedListener(actionEvent ->
            model.getState().setUsageIncluded(view.getUsageIncluded()));
        view.addBssidIncludedListener(actionEvent ->
            model.getState().setBssidIncluded(view.getBssidIncluded()));
        view.addLcrIncludedListener(actionEvent ->
            model.getState().setLcrIncluded(view.getLcrIncluded()));
        view.addMapIncludedListener(actionEvent ->
            model.getState().setMapIncluded(view.getMapIncluded()));

        // Listeners for the input/output file location.
        view.addInputFileNameListener(actionEvent ->
            model.getState().setInputFileName(view.getInputFileName()));
        view.addInputDirListener(actionEvent ->
            model.getState().setInputDir(view.getInputDir()));
        view.addOutputFileNameListener(actionEvent ->
            model.getState().setOutputFileName(view.getOutputFileName()));
        view.addOutputDirListener(actionEvent ->
            model.getState().setOutputDir(view.getOutputDir()));

        // Listeners for updating the buffer.
        view.addReadableListener(actionEvent -> {
            model.getState().setReadable(view.isReadable());
            updateTotalBuffer();
        });
        view.addAndroidVersionListener(actionEvent ->
            model.getState().setAndroidVersionAtLeastS(view.isAndroidVersionAtLeastS()));
        view.addGenerateBufferListener(actionEvent ->
            updateTotalBuffer());

        // Listener for keeping track of which tab was last visited.
        view.addTabbedPaneListener(changeEvent -> {
            if (lastVisitedSubelement != null) {
                updateSubelementBuffer(lastVisitedSubelement);
            }
            lastVisitedSubelement = view.getSelectedTab();
        });

    }

    /**
     * Recalculate and revalidate a particular subelement's buffer.
     *
     * @param subelementName the name of the subelement being updated
     */
    private void updateSubelementBuffer(SubelementName subelementName) {
        if (subelementName == null) {
            return;
        }
        try {
            switch (subelementName) {
                case LCI:
                    model.updateLciSubelementBuffer();
                    break;
                case Z:
                    model.updateZSubelementBuffer();
                    break;
                case USAGE:
                    model.updateUsageSubelementBuffer();
                    break;
                case BSSID:
                    model.updateBssidSubelementBuffer();
                    break;
                case LCR:
                    model.updateLcrSubelementBuffer();
                    break;
                case MAP:
                    model.updateMapSubelementBuffer();
                    break;
            }
        } catch (RuntimeException exception) {
            view.displayErrorMessage(exception.getMessage());
        }
    }

    private void updateTotalBuffer() {
        try {
            HashMap<SubelementName, String> lciSubelementBuffersList = model.getLciSubelementBuffersList();
            HashMap<SubelementName, String> lcrSubelementBuffersList = model.getLcrSubelementBuffersList();
            String notReadableBuffer = getNotReadableBufferDisplay(lciSubelementBuffersList, lcrSubelementBuffersList);
            if (model.getState().isReadable()) {
                String readableBuffer = getReadableBufferDisplay(lciSubelementBuffersList, lcrSubelementBuffersList);
                view.displayBuffer(readableBuffer);
            } else {
                view.displayBuffer(notReadableBuffer);
            }
            writeBufferToFile(notReadableBuffer);
        } catch (RuntimeException exception) {
            view.displayErrorMessage(exception.getMessage());
        }
    }

    private String getReadableBufferDisplay(HashMap<SubelementName, String> lciSubelementBuffersList,
                                            HashMap<SubelementName, String> lcrSubelementBuffersList) {
        StringBuilder totalText = new StringBuilder();
        if (lciSubelementBuffersList.size() > 0) {
            totalText.append("LCI: ");
            for (int i = 0; i < LCI_HEADER.length(); i += 2) {
                totalText.append(LCI_HEADER, i, i + 2);
                if (i < LCI_HEADER.length() - 2) {
                    totalText.append(" ");
                }
            }
            totalText.append("\n");
            for (SubelementName subelementName : lciSubelementBuffersList.keySet()) {
                switch (subelementName)  {
                    case LCI:
                        totalText.append("  LCI subelement: ");
                        break;
                    case Z:
                        totalText.append("  Z subelement: ");
                        break;
                    case USAGE:
                        totalText.append("  Usage Rules/Policy subelement: ");
                        break;
                    case BSSID:
                        totalText.append("  BSSID List subelement: ");
                        break;
                }
                String buffer = lciSubelementBuffersList.get(subelementName);
                for (int i = 0; i < buffer.length(); i += 2) {
                    totalText.append(buffer, i, i + 2);
                    if (i < buffer.length() - 2) {
                        totalText.append(" ");
                    }
                }
                totalText.append("\n");
            }
        }
        if (lcrSubelementBuffersList.size() > 0) {
            totalText.append("\nLCR: ");
            for (int i = 0; i < LCR_HEADER.length(); i += 2) {
                totalText.append(LCR_HEADER, i, i + 2);
                if (i < LCR_HEADER.length() - 2) {
                    totalText.append(" ");
                }
            }
            totalText.append("\n");
            for (SubelementName subelementName : lcrSubelementBuffersList.keySet()) {
                switch (subelementName)  {
                    case LCR:
                        totalText.append("  Location Civic subelement: ");
                        break;
                    case MAP:
                        totalText.append("  Map Image subelement: ");
                        break;
                }
                String buffer = lcrSubelementBuffersList.get(subelementName);
                for (int i = 0; i < buffer.length(); i += 2) {
                    totalText.append(buffer, i, i + 2);
                    if (i < buffer.length() - 2) {
                        totalText.append(" ");
                    }
                }
                totalText.append("\n");
            }
        }
        return totalText.toString();
    }

    private String getNotReadableBufferDisplay(HashMap<SubelementName, String> lciSubelementBuffersList,
                                               HashMap<SubelementName, String> lcrSubelementBuffersList) {
        StringBuilder totalText = new StringBuilder();
        if (lciSubelementBuffersList.size() > 0) {
            totalText.append("# Responder Location Configuration Information (LCI)\n");
            totalText.append("lci=").append(LCI_HEADER);
            for (SubelementName subelementName : lciSubelementBuffersList.keySet()) {
                String buffer = lciSubelementBuffersList.get(subelementName);
                totalText.append(buffer);
            }
            totalText.append("\n");
        }
        if (lcrSubelementBuffersList.size() > 0) {
            if (totalText.length() > 0) {
                totalText.append("\n");
            }
            totalText.append("# Responder Location Civic Report (LCR)\n");
            totalText.append("civic=").append(LCR_HEADER);
            for (SubelementName subelementName : lcrSubelementBuffersList.keySet()) {
                String buffer = lcrSubelementBuffersList.get(subelementName);
                totalText.append(buffer);
            }
            totalText.append("\n");
        }
        return totalText.toString();
    }

    private void writeBufferToFile(String buffer) {
        // TODO(dmevans) write the buffer to the file
    }

}
