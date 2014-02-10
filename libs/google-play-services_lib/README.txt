#-------------------------------------------------------------------------------
# Copyright 2014 if{y} team
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#   http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#-------------------------------------------------------------------------------
Library Project including Google Play services client jar.

This can be used by an Android project to use the API's provided
by Google Play services.

There is technically no source, but the src folder is necessary
to ensure that the build system works. The content is actually
located in the libs/ directory.


USAGE:

Make sure you import this Android library project into your IDE
and set this project as a dependency.

Note that if you use proguard, you will want to include the
options from proguard.txt in your configuration.
