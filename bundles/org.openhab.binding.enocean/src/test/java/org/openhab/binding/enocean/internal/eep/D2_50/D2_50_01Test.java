/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.enocean.internal.eep.D2_50;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.openhab.binding.enocean.internal.messages.ERP1Message;
import org.openhab.core.util.HexUtils;

/**
 * Tests for {@link D2_50_01}.
 *
 * @author Andreas Brenk - Initial contribution
 */
public class D2_50_01Test {

    // D24000002B0087380000000015C0FC0586955E00 00FFFFFFFF3D00
    // D24000002B0087380000000034823A0586955E00 00FFFFFFFF4000
    // D24000002B0087380000000034823A0586955E00 00FFFFFFFF4000
    // D24000002B0087380000000027C1C80586955E00 00FFFFFFFF4000
    // D24000002B0087380000000020A1800586955E00 00FFFFFFFF4000
    // D24000002B0087380000000019E13E0586955E00 00FFFFFFFF4000
    // D24000002B008738000000001140D80586955E00 00FFFFFFFF4000
    // D24000002B008738000000000C60780586955E00 00FFFFFFFF4000

    @Test
    public void testBasicStatus_AUTOMATIC() {
        final byte[] data = HexUtils.hexToBytes("D24B03002B0087380000601841A2D00586955E00");
        final byte[] optionalData = HexUtils.hexToBytes("00FFFFFFFF3D00");
        final byte[] payload = concat(data, optionalData);
        final D2_50_01 message = new D2_50_01(new ERP1Message(data.length, optionalData.length, payload));

        assertThat(message.isValid(), is(true));
        assertThat(message.getMessageType(), is(MessageType.BASIC_STATUS));
        assertThat(message.getOperationMode(), is(OperationMode.AUTOMATIC));
        assertThat(message.isSupplyAirFlapOpened(), is(true));
        assertThat(message.isExhaustAirFlapOpened(), is(true));

        print(message);
    }

    @Test
    public void testBasicStatus_OFF() {
        final byte[] data = HexUtils.hexToBytes("D24000002B0087380000000034823A0586955E00");
        final byte[] optionalData = HexUtils.hexToBytes("00FFFFFFFF3D00");
        final byte[] payload = concat(data, optionalData);
        final D2_50_01 message = new D2_50_01(new ERP1Message(data.length, optionalData.length, payload));

        assertThat(message.isValid(), is(true));
        assertThat(message.getMessageType(), is(MessageType.BASIC_STATUS));
        assertThat(message.getOperationMode(), is(OperationMode.OFF));
        assertThat(message.isSupplyAirFlapOpened(), is(false));
        assertThat(message.isExhaustAirFlapOpened(), is(false));
    }

    @Test
    public void testExtendedStatus() {
        final byte[] data = HexUtils.hexToBytes("D260D80607000000000000000000000586955E00");
        final byte[] optionalData = HexUtils.hexToBytes("00FFFFFFFF3D00");
        final byte[] payload = concat(data, optionalData);
        final D2_50_01 message = new D2_50_01(new ERP1Message(data.length, optionalData.length, payload));

        assertThat(message.isValid(), is(true));
        assertThat(message.getMessageType(), is(MessageType.EXTENDED_STATUS));
    }

    private byte[] concat(byte[] b1, byte[] b2) {
        byte[] b = new byte[b1.length + b2.length];
        System.arraycopy(b1, 0, b, 0, b1.length);
        System.arraycopy(b2, 0, b, b1.length, b2.length);

        return b;
    }

    private void print(D2_50 message) {
        System.out.println("MessageType: " + message.getMessageType());
        System.out.println("OperationMode: " + message.getOperationMode());
        System.out.println("isSafetyModeEnabled: " + message.isSafetyModeEnabled());
        System.out.println("isHeatExchangerBypassOpened: " + message.isHeatExchangerBypassOpened());
        System.out.println("isSupplyAirFlapOpened: " + message.isSupplyAirFlapOpened());
        System.out.println("isExhaustAirFlapOpened: " + message.isExhaustAirFlapOpened());
        System.out.println("isDefrostModeActive: " + message.isDefrostModeActive());
        System.out.println("isCoolingProtectionActive: " + message.isCoolingProtectionActive());
        System.out.println("isOutdoorAirHeaterActive: " + message.isOutdoorAirHeaterActive());
        System.out.println("isSupplyAirHeaterActive: " + message.isSupplyAirHeaterActive());
        System.out.println("isDrainHeaterActive: " + message.isDrainHeaterActive());
        System.out.println("isTimerOperationModeActive: " + message.isTimerOperationModeActive());
        System.out.println("isFilterMaintenanceRequired: " + message.isFilterMaintenanceRequired());
        System.out.println("isWeeklyTimerProgramActive: " + message.isWeeklyTimerProgramActive());
        System.out.println("isRoomTemperatureControlActive: " + message.isRoomTemperatureControlActive());
        System.out.println("isMaster: " + message.isMaster());
        System.out.println("AirQuality1: " + message.getAirQuality1());
        System.out.println("AirQuality2: " + message.getAirQuality2());
        System.out.println("OutdoorAirTemperature: " + message.getOutdoorAirTemperature());
        System.out.println("SupplyAirTemperature: " + message.getSupplyAirTemperature());
        System.out.println("IndoorAirTemperature: " + message.getIndoorAirTemperature());
        System.out.println("ExhaustAirTemperature: " + message.getExhaustAirTemperature());
    }
}
