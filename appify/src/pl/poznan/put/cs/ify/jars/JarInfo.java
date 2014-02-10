/*******************************************************************************
 * Copyright 2014 if{y} team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package pl.poznan.put.cs.ify.jars;

public class JarInfo {

	private String mClassName;
private String mJarPath;

	public JarInfo(String className, String jarPath) {
		super();
		mClassName = className;
		mJarPath = jarPath;
	}

	public String getClassName() {
		return mClassName;
	}

	public void setClassName(String className) {
		mClassName = className;
	}

	public String getJarPath() {
		return mJarPath;
	}

	public void setJarPath(String jarPath) {
		mJarPath = jarPath;
	}
}
