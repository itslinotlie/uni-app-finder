<img src="https://imgur.com/RL3Qspf.png">

###### Designed by @itslinotlie

The 2nd rendition of the ICS4U group project series. The goal of this project is to match students to their ideal Engineering Unversity(s) in Ontario. Each group member is responsible for designing and implementing a unique matching method, whether by the means of a survey or visually displaying the geolocations of the universities. Everyone contributed to the project together, including, but not limited to, user login, look & feel, and data collection.

## My Major Contributions

### Introducing Git

<a href="https://youtu.be/SCn8z1hJzTk"><img src="https://imgur.com/C0zhYJw.png"></a>

###### pssss, this is clickable

A typical high-school group project usually consists of many files being shared over Google Drive. I've even seen people transfer code through Google Docs... Needless to say, this is very inefficient compared to using Git. I introduced and integrated Git into our workflow by creating the above video and the occasional  git-support. Initially, there was a large learning curve with using the terminal and learning the syntax of Git. However, over a week, everyone was accustomed to adding+commiting their changes and creating a pull request to merge their changes. 

### Map Screen

<img src="https://imgur.com/uJCRNFK.png">

###### The very extra loading gif that required a big detour

<img src="https://imgur.com/S7QT1Vk.png">

On the surface, my features/screen(s) aren't extremely impressive. Users can pinpoint their location on a map of the GTA or enter their postal code through a textbox. Afterwards, distances of the Ontario universities are calculated and displayed visually on a map of Ontario. If the user's locations fall within the map, they are shown as well. Since there was a predetermined colour theme (inspired by Hacktoberfest), the screen was visually pleasing.

The "technically challenging-ish" components:

- Establishing a connection with websites via Java's HttpURLConnection. This allowed me to access geocoder.ca's API and determine the longitude/latitude coordinates from a given postal code. Parsing the JSON into objects required some ReGeX expressions, but the process was pretty trivial.
    - Due to the long response times from the website, I added a loading gif as the data was being received (and no Mr. Fernandes, it's not fake :/). However, due to Java's Swing design with their single-threaded programming model, this was a challenge. I needed to "multi-thread" with Java's SwingWorker to accomplish two tasks at once, receiving data from a website and displaying a loading gif. This was the most discrete, but also the most technically challenging aspect of my feature that can easily be missed.
- Determining the distance between two longitude/latitude coordinates. Although the Haversine formula is straightforwards, a formula that calculates the "great-circle distance" between two points on a sphere, I tried to understand it. In the end, I realized the mathematical reasoning was well beyond the scopes of this project and my abilities ): 

## Group Contributions

A thorough description of everyone's contributions can be found on their respective READMEs or [partial information here](src/main/Launcher.java), but here is the tldr;

#### @jeffreyjxang | Jeffrey
- Data collection
- University Information Screen

#### @JordanChew | Jordan
- GUI consultant 
- Account information
- Dashboard Screen

#### @mithun0614 | Mithun
- Saving/loading user information
- Book-marking universities

#### @349708008 | Brittany
- Look & Feel
- Survey Screen

#### @itslinotlie | Me (Michael)
- GIT consultant
- Managing the workflow
- View Map Screen