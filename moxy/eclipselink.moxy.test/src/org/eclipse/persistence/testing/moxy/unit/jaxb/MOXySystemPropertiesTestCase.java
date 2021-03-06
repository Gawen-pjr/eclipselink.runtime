/*******************************************************************************
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *  - Martin Vojtek - 2.6.0 - Initial implementation
 ******************************************************************************/
package org.eclipse.persistence.testing.moxy.unit.jaxb;

import static org.junit.Assert.assertTrue;
import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

import org.eclipse.persistence.internal.security.PrivilegedAccessHelper;
import org.eclipse.persistence.jaxb.MOXySystemProperties;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests MOXySystemProperties class.
 *
 * @author Martin Vojtek
 *
 */
@RunWith(JMockit.class)
public class MOXySystemPropertiesTestCase {

    @Test
    public void getBooleanWithSecurityManager() {

        final String propertyName = "propertyName";

        new Expectations(PrivilegedAccessHelper.class, MOXySystemProperties.class) {{
            PrivilegedAccessHelper.shouldUsePrivilegedAccess(); result = true;
            Deencapsulation.invoke(MOXySystemProperties.class, "runDoPrivileged", propertyName); result = true;
        }};

        Boolean result = Deencapsulation.invoke(MOXySystemProperties.class, "getBoolean", propertyName);

        assertTrue(result);

    }

    @Test
    public void getBooleanWithoutSecurityManager() {

        final String propertyName = "propertyName";

        new Expectations(PrivilegedAccessHelper.class, MOXySystemProperties.class) {{
            PrivilegedAccessHelper.shouldUsePrivilegedAccess(); result = false;
            Deencapsulation.invoke(MOXySystemProperties.class, "getSystemPropertyValue", propertyName); result = true;
        }};

        Boolean result = Deencapsulation.invoke(MOXySystemProperties.class, "getBoolean", propertyName);

        assertTrue(result);

    }
}
