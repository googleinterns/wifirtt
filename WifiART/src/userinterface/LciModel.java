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

import structs.AltitudeType;
import structs.LciState;
import structs.MapDatum;
import structs.Subelement;

public class LciModel implements Subelement {

    // Constants
    private static final byte SUBELEMENT_ID = 0;
    private static final int MAX_LATITUDE_UNCERTAINTY_ENCODING = 34;
    private static final int MIN_LATITUDE_UNCERTAINTY_ENCODING = 1;
    private static final int MAX_LONGITUDE_UNCERTAINTY_ENCODING = 34;
    private static final int MIN_LONGITUDE_UNCERTAINTY_ENCODING = 1;
    private static final int MAX_ALTITUDE_UNCERTAINTY_ENCODING = 30;
    private static final int MIN_ALTITUDE_UNCERTAINTY_ENCODING = 1;

    // Indices of field groups (in bytes)
    private static final int SUBELEMENT_ID_INDEX = 0;
    private static final int LENGTH_INDEX = 1;
    private static final int LATITUDE_FIELDS_INDEX = 2;
    private static final int LONGITUDE_FIELDS_INDEX = 7;
    private static final int ALTITUDE_FIELDS_INDEX = 12;
    private static final int MISCELLANEOUS_FIELDS_INDEX = 17;

    // Lengths of field groups (in bytes)
    private static final int SUBELEMENT_ID_LENGTH = 1;
    private static final int LENGTH_FIELD_LENGTH = 1;
    private static final int LATITUDE_FIELDS_LENGTH = 5;
    private static final int LONGITUDE_FIELDS_LENGTH = 5;
    private static final int ALTITUDE_FIELDS_LENGTH = 5;
    private static final int MISCELLANEOUS_FIELDS_LENGTH = 1;

    // Indices of fields within groups (in bits)
    private static final int LATITUDE_UNCERTAINTY_INDEX = 0;
    private static final int LATITUDE_INDEX = 6;

    private static final int LONGITUDE_UNCERTAINTY_INDEX = 0;
    private static final int LONGITUDE_INDEX = 6;

    private static final int ALTITUDE_UNITS_INDEX = 0;
    private static final int ALTITUDE_UNCERTAINTY_INDEX = 4;
    private static final int ALTITUDE_INDEX = 10;

    private static final int MAP_DATUM_INDEX = 0;
    private static final int REG_LOC_AGREEMENT_INDEX = 3;
    private static final int REG_LOC_DSE_INDEX = 4;
    private static final int DEPENDENT_STA_INDEX = 5;
    private static final int VERSION_INDEX = 6;

    // Lengths of fields within groups (in bits)
    private static final int LATITUDE_UNCERTAINTY_LENGTH = 6;
    private static final int LATITUDE_LENGTH = 34;

    private static final int LONGITUDE_UNCERTAINTY_LENGTH = 6;
    private static final int LONGITUDE_LENGTH = 34;

    private static final int ALTITUDE_UNITS_LENGTH = 4;
    private static final int ALTITUDE_UNCERTAINTY_LENGTH = 6;
    private static final int ALTITUDE_LENGTH = 30;

    private static final int MAP_DATUM_LENGTH = 3;
    private static final int REG_LOC_AGREEMENT_LENGTH = 1;
    private static final int REG_LOC_DSE_LENGTH = 1;
    private static final int DEPENDENT_STA_LENGTH = 1;
    private static final int VERSION_LENGTH = 2;


    private LciState state;
    private LciController fc;

    /**
     * Constructor.
     *
     * @param state the system state
     */
    public LciModel(LciState state) {
        this.state = state;
    }

    /**
     * Get the current LCI subelement state from the model.
     *
     * @return the LCI subelement state
     */
    public LciState getState() {
        return this.state;
    }

    /**
     * Set the LCI subelement state in the model.
     * @param state the LCI subelement state
     */
    public void setState(LciState state) {
        this.state = state;
    }


    /**
     * The callback into the LCI controller used when an asynchronous even occurs.
     *
     * @param fc the LCI controller in the MVC pattern
     */
    public void setCallback(LciController fc) {
        this.fc = fc;
    }


    @Override
    public String toHexBuffer() {
        byte fieldsLength = LATITUDE_FIELDS_LENGTH + LONGITUDE_FIELDS_LENGTH
            + ALTITUDE_FIELDS_LENGTH + MISCELLANEOUS_FIELDS_LENGTH;
        byte[] buffer = new byte[SUBELEMENT_ID_LENGTH + LENGTH_FIELD_LENGTH + fieldsLength];

        buffer[SUBELEMENT_ID_INDEX] = SUBELEMENT_ID;
        buffer[LENGTH_INDEX] = fieldsLength;

        long latitudeFields = getLatitudeFields();
        fillLittleEndian(buffer, latitudeFields, LATITUDE_FIELDS_INDEX, LATITUDE_FIELDS_LENGTH);
        long longitudeFields = getLongitudeFields();
        fillLittleEndian(buffer, longitudeFields, LONGITUDE_FIELDS_INDEX, LONGITUDE_FIELDS_LENGTH);
        long altitudeFields = getAltitudeFields();
        fillLittleEndian(buffer, altitudeFields, ALTITUDE_FIELDS_INDEX, ALTITUDE_FIELDS_LENGTH);
        int miscellaneousFields = getMiscellaneousFields();
        fillLittleEndian(buffer, miscellaneousFields, MISCELLANEOUS_FIELDS_INDEX, MISCELLANEOUS_FIELDS_LENGTH);

        String result = "";
        for (byte b : buffer) {
            result += String.format("%02x", b); // Convert the byte to a hex string of 2 characters.
        }
        return result;
    }

    private long getLatitudeFields() {
        double latitudeUncertainty = state.getLatitudeUncertainty();
        // LatUnc = 8 - log_2(uncertainty) -> rounded down
        int latitudeUncertaintyEncoding = 0;
        if (latitudeUncertainty > 0) { // User has provided an longitude uncertainty
            latitudeUncertaintyEncoding = (int) (8 - Math.log(latitudeUncertainty) / Math.log(2));
            if (latitudeUncertaintyEncoding > MAX_LATITUDE_UNCERTAINTY_ENCODING) {
                latitudeUncertaintyEncoding = MAX_LATITUDE_UNCERTAINTY_ENCODING;
            } else if (latitudeUncertaintyEncoding < MIN_LATITUDE_UNCERTAINTY_ENCODING) {
                latitudeUncertaintyEncoding = MIN_LATITUDE_UNCERTAINTY_ENCODING;
            }
        }

        double latitude = state.getLatitude();
        long latitudeEncoding = Math.round(latitude * 0x0000000002000000L);
        if (latitude < 0) {
            latitudeEncoding = 0x0000000400000000L - latitudeEncoding;
        }

        long result = 0;
        result |= ((latitudeUncertaintyEncoding << LATITUDE_UNCERTAINTY_INDEX) & 0x000000000000003fL);
        result |= ((latitudeEncoding << LATITUDE_INDEX) & 0x000000ffffffffc0L);
        return result;
    }

    private long getLongitudeFields() {
        double longitudeUncertainty = state.getLongitudeUncertainty();
        // LongUnc = 8 - log_2(uncertainty) -> rounded down
        int longitudeUncertaintyEncoding = 0;
        if (longitudeUncertainty > 0) { // User has provided an longitude uncertainty
            longitudeUncertaintyEncoding = (int) (8 - Math.log(longitudeUncertainty) / Math.log(2));
            if (longitudeUncertaintyEncoding > MAX_LONGITUDE_UNCERTAINTY_ENCODING) {
                longitudeUncertaintyEncoding = MAX_LONGITUDE_UNCERTAINTY_ENCODING;
            } else if (longitudeUncertaintyEncoding < MIN_LONGITUDE_UNCERTAINTY_ENCODING) {
                longitudeUncertaintyEncoding = MIN_LONGITUDE_UNCERTAINTY_ENCODING;
            }
        }

        double longitude = state.getLongitude();
        long longitudeEncoding = Math.round(longitude * 0x0000000002000000L);
        if (longitude < 0) {
            longitudeEncoding = 0x0000000400000000L - longitudeEncoding;
        }

        long result = 0;
        result |= ((longitudeUncertaintyEncoding << LONGITUDE_UNCERTAINTY_INDEX) & 0x000000000000003fL);
        result |= ((longitudeEncoding << LONGITUDE_INDEX) & 0x000000ffffffffc0L);
        return result;
    }

    private long getAltitudeFields() {
        AltitudeType altitudeType = state.getAltitudeType();
        int altitudeTypeEncoding = altitudeType.getEncoding();

        double altitudeUncertainty = state.getAltitudeUncertainty();
        // AltUnc = 21 - log_2(uncertainty) -> rounded down
        int altitudeUncertaintyEncoding = 0;
        if (altitudeUncertainty > 0) { // User has provided an altitude uncertainty
            altitudeUncertaintyEncoding = (int) (21 - Math.log(altitudeUncertainty) / Math.log(2));
            if (altitudeUncertaintyEncoding > MAX_ALTITUDE_UNCERTAINTY_ENCODING) {
                altitudeUncertaintyEncoding = MAX_ALTITUDE_UNCERTAINTY_ENCODING;
            } else if (altitudeUncertaintyEncoding < MIN_ALTITUDE_UNCERTAINTY_ENCODING) {
                altitudeUncertaintyEncoding = MIN_ALTITUDE_UNCERTAINTY_ENCODING;
            }
        }

        double altitude = state.getAltitude();
        long altitudeEncoding = Math.round(altitude * 0x0000000000000100L);
        if (altitude < 0) {
            altitudeEncoding = 0x0000000040000000L - altitudeEncoding;
        }

        long result = 0;
        result |= ((altitudeTypeEncoding << ALTITUDE_UNITS_INDEX) & 0x000000000000000fL);
        result |= ((altitudeUncertaintyEncoding << ALTITUDE_UNCERTAINTY_INDEX) & 0x00000000000003f0L);
        result |= ((altitudeEncoding << ALTITUDE_INDEX) & 0x000000fffffffc00L);
        return result;
    }

    private int getMiscellaneousFields() {
        MapDatum mapDatum = state.getMapDatum();
        int mapDatumEncoding = mapDatum.getEncoding();

        boolean regLocAgreement = state.getRegLocAgreement();
        int regLocAgreementEncoding = regLocAgreement ? 1 : 0;

        boolean regLocDse = state.getRegLocDse();
        int regLocDseEncoding = regLocDse ? 1 : 0;

        boolean dependentSta = state.getDependentSta();
        int dependentStaEncoding = dependentSta ? 1 : 0;

        int version = state.getLciVersion();

        int result = 0;
        result |= ((mapDatumEncoding << MAP_DATUM_INDEX) & 0x00000007);
        result |= ((regLocAgreementEncoding << REG_LOC_AGREEMENT_INDEX) & 0x00000008);
        result |= ((regLocDseEncoding << REG_LOC_DSE_INDEX) & 0x00000010);
        result |= ((dependentStaEncoding << DEPENDENT_STA_INDEX) & 0x00000020);
        result |= ((version << VERSION_INDEX) & 0x000000c0);
        return result;
    }

    /**
     * Insert an integer into a byte array in little-endian format.
     *
     * @param arr The byte array being populated
     * @param num The integer to insert into the array
     * @param startIndex The starting index that the integer should appear in the array
     * @param length The number of bytes being populated
     */
    private void fillLittleEndian(byte[] arr, long num, int startIndex, int length) {
        for (int i = 0; i < length; i++) {
            arr[startIndex + i] = (byte)(num & 0xff); // Insert the least-significant byte into the array.
            num >>= 8; // Move the next byte into the least-significant position.
        }
    }

}
