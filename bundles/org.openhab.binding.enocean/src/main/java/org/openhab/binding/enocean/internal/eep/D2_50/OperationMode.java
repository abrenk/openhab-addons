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

/**
 * @author Andreas Brenk - Initial contribution
 */

enum OperationMode {
    OFF,
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4,
    /**
     * 11
     */
    AUTOMATIC,
    /**
     * 12
     */
    AUTOMATIC_ON_DEMAND,
    /**
     * 13
     */
    SUPPLY_AIR_ONLY,
    /**
     * 14
     */
    EXHAUST_AIR_ONLY;

    private static final OperationMode values[] = values();

    public static OperationMode valueOf(int b) {
        if (b >= 0 && b <= 4) {
            return values[b];
        }

        if (b >= 11 && b <= 14) {
            return values[b - 6];
        }

        throw new IllegalArgumentException();
    }
}
