package com.github.johnreedlol.internal;

import java.net.URL;
import java.security.CodeSource;

/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 *
 * Given a classname locate associated PackageInfo (jar name, version name).
 *
 * @author James Strachan
 * @Ceki G&uuml;lc&uuml;
 */
final class PackagingDataCalculator {

    static String getCodeLocation(Class type) {
        try {
            if (type != null) {
                // file:/C:/java/maven-2.0.8/repo/com/icegreen/greenmail/1.3/greenmail-1.3.jar
                CodeSource codeSource = type.getProtectionDomain().getCodeSource();
                if (codeSource != null) {
                    URL resource = codeSource.getLocation();
                    if (resource != null) {
                        String locationStr = resource.toString();
                        // now lets remove all but the file name
                        String result = getCodeLocation(locationStr, '/');
                        if (result != null) {
                            return result;
                        }
                        return getCodeLocation(locationStr, '\\');
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return "";
    }

    static private String getCodeLocation(String locationStr, char separator) {
        int idx = locationStr.lastIndexOf(separator);
        if (isFolder(idx, locationStr)) {
            idx = locationStr.lastIndexOf(separator, idx - 1);
            return locationStr.substring(idx + 1);
        } else if (idx > 0) {
            return locationStr.substring(idx + 1);
        }
        return null;
    }

    static private boolean isFolder(int idx, String text) {
        return (idx != -1 && idx + 1 == text.length());
    }

}
