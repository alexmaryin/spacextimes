# SpaceX Times
###### [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![build](https://github.com/alexmaryin/spacextimes/actions/workflows/build.yml/badge.svg)](https://github.com/alexmaryin/spacextimes/actions/workflows/build.yml)

Android application for fetching all information about SpaceX&trade; rocket's launches and landings, posted media, vehicles technical data, crew members and over more. 

Application runs on [r/spaceX-API](https://github.com/r-spacex/SpaceX-API) engine version 4.

<a href="https://play.google.com/store/apps/details?id=ru.alexmaryin.spacextimes_rx" target="_blank"><img src="https://github.com/steverichey/google-play-badge-svg/blob/master/img/en_get.svg"  alt="Get in on Google play" height="80px" ></a>
<a href="https://www.buymeacoffee.com/java73" target="_blank"><img src="https://cdn.buymeacoffee.com/buttons/v2/default-yellow.png" alt="Buy Me A Coffee" height="80px" ></a>

**Already launched:**

- [x] Capsules list
- [x] Cores list
- [x] Crew members list, member details
- [x] Dragons list, dragon details
- [x] Rockets list, rocket details
- [x] Launch pads list with google-map location, pad details
- [x] Landing pads list with google-map location, pad details
- [x] Launches list, launches details, filtering and fast search next launch
- [x] Payloads details with satellite data
- [x] Company milestone events 
- [x] Navigation between elements

**To do:**
- [ ] NORADs integration, Starlink screen

**Interface languages:**
- [x] Russian
- [x] English

**Features:**
* Auto-translation for english descriptions and remarks by [FastTranslator](https://fasttranslator.herokuapp.com/)
* Wiki-pages interlanguage auto-selecting with [Jsoup](https://jsoup.org/)
* Cache for translations on Room (sqlite)
* Cache for fetched data on Room (sqlite) with sheduling for sync
* Downloading full-format photo of launch, vehicle or crew member to device
* Performance, speed of data fetching improved (max of 10..65 ms for response on my own device)

**English**

![screen 1](/readme_images/screenshot_1.png)
![screen 2](/readme_images/screenshot_2.png)
![screen 3](/readme_images/screenshot_3.png)
![screen 4](/readme_images/screenshot_4.png)


**Russian**

![screen 5](/readme_images/screenshot_5.png)
![screen 6](/readme_images/screenshot_6.png)
![screen 7](/readme_images/screenshot_7.png)
![screen 8](/readme_images/screenshot_8.png)

**License**
```
Copyright 2021 Alex Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
