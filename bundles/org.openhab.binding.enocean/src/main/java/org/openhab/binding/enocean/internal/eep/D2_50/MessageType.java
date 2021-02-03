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
enum MessageType {
    REMOTE_TRANSMISSION_REQUEST,
    CONTROL,
    BASIC_STATUS,
    EXTENDED_STATUS;

    private static final MessageType values[] = values();

    public static MessageType valueOf(int b) {
        if (b < 0 || b > values.length) {
            throw new IllegalArgumentException();
        }

        return values[b];
    }
}
