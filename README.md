# Movie Speed Picker
Android project that will display a list of movies and thier information after selecting a list of criteria.
The idea of the app was to select a popular movie as quickly as possible with little input necessary.
<br/>
The API that I used to create this app is from themoviedb.org
More information about the API can be found at [their website](https://www.themoviedb.org/documentation/api).

## Version 1

### Home Screen
The home screen shows three different categories to choose from:
- Genre
- Rating (based on Motion Picture Association of America)
- Length
<br/>
<p align="center">
  <img src="http://keenanmueller.com/MovieSpeedPicker/images/screenshot1.png" width="288" height="512" title="Home Screen"> 
  <img src="http://keenanmueller.com/MovieSpeedPicker/images/screenshot2.png" width="288" height="512" title="Selection">
</p>

After selecting the desired settings from the dropdown, you just need to click the start search button and it will take you to the "List View"

### List View
<p align="center">
  <img src="http://keenanmueller.com/MovieSpeedPicker/images/screenshot3.png" width="288" height="512" title="Search Ready"> 
  <img src="http://keenanmueller.com/MovieSpeedPicker/images/screenshot4.png" width="288" height="512" title="Home Screen">
</p>

The List View uses a Recycler View to list objects that displays the title, average rating, and the poster of a movie. The pictures are displayed using [Picasso](http://square.github.io/picasso/).
The current Version only displays the top 20 movies based on popularity, but further versions will include a "show more" button. The sorting is based on popularity because I did not want to solely use highest rating. Highest rating returned movies that were rated perfectly but only had 1 or 2 ratings. 
<br/>

After finding the desired movie in the list, select a movie by clicking anywhere on the horizontally listed segment containing the movie. This will take you to the Movie Detail screen.

### Detail View
<p align="center">
  <img src="http://keenanmueller.com/MovieSpeedPicker/images/screenshot5.png" width="288" height="512" title="Movie Detail"> 
</p>

The Detail view displays information that is used by imdb.com and accessed through themoviedb.org.  In future versions I will most likely display more information.
<br/>

## Built With

* [The Movie DB](https://www.themoviedb.org/documentation/api) - API
* [Picasso](http://square.github.io/picasso/) - Used to display the movie poster images
* Android Studio - Front end
* Java - Server
