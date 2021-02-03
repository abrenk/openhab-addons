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

import static org.openhab.binding.enocean.internal.EnOceanBindingConstants.*;

import java.util.function.Function;

import org.openhab.binding.enocean.internal.eep.Base._VLDMessage;
import org.openhab.binding.enocean.internal.messages.ERP1Message;
import org.openhab.core.config.core.Configuration;
import org.openhab.core.library.types.*;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;
import org.openhab.core.types.State;
import org.openhab.core.types.UnDefType;

/**
 * Base class for EEP D2-50: Heat Recovery Ventilation.
 * <p>
 * Supported by e.g.
 * <ul>
 * <li>Dimplex DL 50 WE2</li>
 * <li>Dimplex DL 50 WH2</li>
 * </ul>
 * <p>
 * Documentation:
 * <ul>
 * <li>http://tools.enocean-alliance.org/EEPViewer/profiles/D2/50/00/D2-50-00.pdf</li>
 * <li>http://tools.enocean-alliance.org/EEPViewer/profiles/D2/50/01/D2-50-01.pdf (same as D2-50-00)</li>
 * <li>http://tools.enocean-alliance.org/EEPViewer/profiles/D2/50/10/D2-50-10.pdf (same as D2-50-00)</li>
 * <li>http://tools.enocean-alliance.org/EEPViewer/profiles/D2/50/11/D2-50-11.pdf (same as D2-50-00)</li>
 * <li>https://www.enocean-alliance.org/wp-content/uploads/2017/05/EnOcean_Equipment_Profiles_EEP_v2.6.7_public.pdf</li>
 * </ul>
 *
 * @author Andreas Brenk - Initial contribution
 */
public abstract class D2_50 extends _VLDMessage {

    /**
     * Constructs a D2-50-XX message for sending.
     */
    public D2_50() {
        super();
    }

    /**
     * Constructs a D2-50-XX message for a received {@link ERP1Message}.
     */
    public D2_50(ERP1Message packet) {
        super(packet);
    }

    @Override
    protected State convertToStateImpl(String channelId, String channelTypeId,
            Function<String, State> getCurrentStateFunc, Configuration config) {
        switch (getMessageType()) {
            case BASIC_STATUS:
                switch (channelId) {
                    case CHANNEL_OPERATION_MODE:
                        return new StringType(String.valueOf(getOperationMode()));
                    case CHANNEL_SAFETY_MODE:
                        return isSafetyModeEnabled() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_HEAT_EXCHANGER_BYPASS:
                        return isHeatExchangerBypassOpened() ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
                    case CHANNEL_SUPPLY_AIR_FLAP_POSITION:
                        return isSupplyAirFlapOpened() ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
                    case CHANNEL_EXHAUST_AIR_FLAP_POSITION:
                        return isExhaustAirFlapOpened() ? OpenClosedType.OPEN : OpenClosedType.CLOSED;
                    case CHANNEL_DEFROST_MODE:
                        return isDefrostModeActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_COOLING_PROTECTION:
                        return isCoolingProtectionActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_OUTDOOR_AIR_HEATER:
                        return isOutdoorAirHeaterActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_SUPPLY_AIR_HEATER:
                        return isSupplyAirHeaterActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_DRAIN_HEATER:
                        return isDrainHeaterActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_TIMER_OPERATION_MODE:
                        return isTimerOperationModeActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_FILTER_MAINTENANCE:
                        return isFilterMaintenanceRequired() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_WEEKLER_TIMER_PROGRAM:
                        return isWeeklyTimerProgramActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_ROOM_TEMPERATURE_CONTROL:
                        return isRoomTemperatureControlActive() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_AIR_QUALITY_SENSOR_1:
                        return new PercentType(getAirQuality1());
                    case CHANNEL_MASTER_SLAVE_CONFIGURATION:
                        return isMaster() ? OnOffType.ON : OnOffType.OFF;
                    case CHANNEL_AIR_QUALITY_SENSOR_2:
                        return new PercentType(getAirQuality2());
                    case CHANNEL_OUTDOOR_AIR_TEMPERATURE:
                        return new QuantityType<>(getOutdoorAirTemperature(), SIUnits.CELSIUS);
                    case CHANNEL_SUPPLY_AIR_TEMPERATURE:
                        return new QuantityType<>(getSupplyAirTemperature(), SIUnits.CELSIUS);
                    case CHANNEL_INDOOR_AIR_TEMPERATURE:
                        return new QuantityType<>(getIndoorAirTemperature(), SIUnits.CELSIUS);
                    case CHANNEL_EXHAUST_AIR_TEMPERATURE:
                        return new QuantityType<>(getExhaustAirTemperature(), SIUnits.CELSIUS);
                    case CHANNEL_SUPPLY_FAN_FLOW_RATE:
                        return new QuantityType<>(getSupplyFanFlowRate(), Units.CUBICMETRE_PER_HOUR);
                    case CHANNEL_EXHAUST_FAN_FLOW_RATE:
                        return new QuantityType<>(getExhaustFanFlowRate(), Units.CUBICMETRE_PER_HOUR);
                    case CHANNEL_SUPPLY_FAN_SPEED:
                        return new QuantityType<>(getSupplyFanSpeed(), Units.HERTZ);
                    case CHANNEL_EXHAUST_FAN_SPEED:
                        return new QuantityType<>(getExhaustFanSpeed(), Units.HERTZ);
                    default:
                        return UnDefType.UNDEF;
                }
            case EXTENDED_STATUS:
                return UnDefType.UNDEF; // TODO MessageType.EXTENDED_STATUS
            default:
                return UnDefType.UNDEF;
        }
    }

    protected MessageType getMessageType() {
        return MessageType.valueOf(((bytes[0] & 0xFF) >> 5)); // TODO is "& 0xFF" needed?
    }

    protected OperationMode getOperationMode() {
        return OperationMode.valueOf((bytes[0] & 0x0F));
    }

    protected boolean isSafetyModeEnabled() {
        return getBit(bytes[1], 3);
    }

    protected boolean isHeatExchangerBypassOpened() {
        return getBit(bytes[1], 2);
    }

    protected boolean isSupplyAirFlapOpened() {
        return getBit(bytes[1], 1);
    }

    protected boolean isExhaustAirFlapOpened() {
        return getBit(bytes[1], 0);
    }

    protected boolean isDefrostModeActive() {
        return getBit(bytes[2], 7);
    }

    protected boolean isCoolingProtectionActive() {
        return getBit(bytes[2], 6);
    }

    protected boolean isOutdoorAirHeaterActive() {
        return getBit(bytes[2], 5);
    }

    protected boolean isSupplyAirHeaterActive() {
        return getBit(bytes[2], 4);
    }

    protected boolean isDrainHeaterActive() {
        return getBit(bytes[2], 3);
    }

    protected boolean isTimerOperationModeActive() {
        return getBit(bytes[2], 2);
    }

    protected boolean isFilterMaintenanceRequired() {
        return getBit(bytes[2], 1);
    }

    protected boolean isWeeklyTimerProgramActive() {
        return getBit(bytes[2], 0);
    }

    protected boolean isRoomTemperatureControlActive() {
        return getBit(bytes[3], 7);
    }

    protected boolean isMaster() {
        return getBit(bytes[4], 7);
    }

    protected int getAirQuality1() {
        return bytes[3] & 0x7F;
    }

    protected int getAirQuality2() {
        return bytes[4] & 0x7F;
    }

    protected int getOutdoorAirTemperature() {
        return ((bytes[5] & 0xFF) >> 1) - 64;
    }

    protected int getSupplyAirTemperature() {
        return ((bytes[5] & 0x7F) << 6 | bytes[6] >> 2) - 64; // TODO
    }

    protected int getIndoorAirTemperature() {
        return 0; // TODO
    }

    protected int getExhaustAirTemperature() {
        return 0; // TODO
    }

    protected int getSupplyFanFlowRate() {
        return 0; // TODO 0-1023 m3/h
    }

    protected int getExhaustFanFlowRate() {
        return 0; // TODO 0-1023 m3/h
    }

    protected int getSupplyFanSpeed() {
        // see https://en.wikipedia.org/wiki/Revolutions_per_minute
        return 0; // TODO 0-4095 1/min, byte 11-12,5
    }

    protected int getExhaustFanSpeed() {
        return 0; // TODO 0-4095 1/min, byte 12,5-13
    }
}
