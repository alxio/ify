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
package pl.poznan.put.cs.ify.api.core;

import pl.poznan.put.cs.ify.api.security.User;
import pl.poznan.put.cs.ify.api.security.YSecurity.ILoginCallback;

/**
 * Interface for class responsible of handling sining in and out of Recipes
 * Server and storing information about logged user.
 */
public interface ISecurity {

	User getCurrentUser();

	void logout(ILoginCallback cb);

	void login(String username, String password, ILoginCallback cb);

}
