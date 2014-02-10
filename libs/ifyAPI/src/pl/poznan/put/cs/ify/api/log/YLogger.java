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
package pl.poznan.put.cs.ify.api.log;

import pl.poznan.put.cs.ify.api.IYRecipeHost;

public class YLogger {
	private String mTag;
	private IYRecipeHost mHost;

	public YLogger(String tag, IYRecipeHost host) {
		mTag = tag;
		mHost = host;
	}

	public void getLogs() {
		YLog.getFilteredHistory("<Y>" + mTag);
	}

	// TODO Refacetor d,e, etc to use it
	public void println(int priority, String msg) {
		YLog.println(priority, mTag, msg);
		mHost.sendArchivedLogs(mTag);
	}

	public void d(String msg) {
		println(YLog.DEBUG, msg);
	}

	public void e(String msg) {
		println(YLog.ERROR, msg);
	}

	public void i(String msg) {
		println(YLog.INFO, msg);
	}

	public void v(String msg) {
		println(YLog.VERBOSE, msg);
	}

	public void w(String msg) {
		println(YLog.WARN, msg);
	}

	public void wtf(String msg) {
		println(YLog.ASSERT, msg);
	}
}
