/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package cz.mallat.uasparser;

import java.io.IOException;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class MyBenchmark {
    
    private static final String TEST_UA = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1;)";
    
    private UASparser uasParser;
    private UserAgentStringParser uadParser;
    
    private UASparser uasSParser;
    
    @Setup
    public void setup() throws IOException {
        uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
        uadParser = UADetectorServiceFactory.getResourceModuleParser();
        
        uasSParser = new SingleThreadedUASparser(OnlineUpdater.getVendoredInputStream());
    }

    @Benchmark
    public UserAgentInfo testUasParser() throws IOException {
        return uasParser.parse(TEST_UA);        
    }
    
    @Benchmark
    public UserAgentInfo testSingleThreadedUasParser() throws IOException {
        return uasSParser.parse(TEST_UA);        
    }
    
    @Benchmark
    public ReadableUserAgent testUaDetector() {
        return uadParser.parse(TEST_UA);
    }

}
