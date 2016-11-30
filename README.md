# In Rotation
[![Build Status](https://travis-ci.org/cpe305/fall2016-project-AndrewCofano.svg?branch=master)](https://travis-ci.org/cpe305/fall2016-project-AndrewCofano)

To see more visuals about the project, please [go to Project Page](https://cpe305.github.io/fall2016-project-AndrewCofano/).

This is the next step in the music experience. The dangerous disputes over the aux cord... are over. With In Rotation, the music in the car or at the crazy party you're having will be a group effort, mixing the best and most vibin music for everyone. Sorry iOS, Android gets the front seat today. Simply download the application onto your Android phone, authenticate with [Spotify](http://www.spotify.com) and start spinning what's in your rotation.   

## User Experience
The interface for In Rotation is simple and straightforward, helping you get hyped a little faster. The steps are easy and demonstrated below:

1. Authenticate with Spotify.
2. Create a new playlist from your home screen.
3. Search the Spotify Music Library for your favorite song to start the playlist.
4. Enjoy your playlist and enjoy collaborating with your friends!

*See [Project Page](https://cpe305.github.io/fall2016-project-AndrewCofano/) for more descriptive screenshots*

## Overview of the System Architecture
### The System Architecture Overview
The application is structured in a 3-tiered Model-View-Presenter (MVP) Architecture. This allows the code of the system to be separated into understandable components. In the *View* resides the Android XML layouts. In the *Presenter*, the interaction between users and the playlist data is handled. Finally the structure of business logic classes are stored in the *Model*, including classes such as a Playlist, Song, HostUser, and more. 

As an additional layer to the system architecture, the application provides support for the Spotify Music Library (through the Spotify SDK & Web API) and a Firebase Real-time database. This data layer interacts with the Model of the system to provide music and real-time collaboration between users.

### Class Diagram - A User and the Playlists
One instance of a comprehensive relationship between classes is the relationship between a HostUser and the user's playlists. Please see the [Project Page](https://cpe305.github.io/fall2016-project-AndrewCofano/) for more explanation.

## Technologies
Extending past the standard Android application, In Rotation was built on many current and powerful technologies. Some of them include:
* Spotify [SDK](https://developer.spotify.com/technologies/spotify-android-sdk/) and [Web API](https://developer.spotify.com/web-api/)
* Firebase [Real-Time Database](https://firebase.google.com/) for quick updates to active playlists and users in the system
* Volley HTTP [Request Library](https://developer.android.com/training/volley/index.html)

## Design Patterns Used
To achieve high performance and a foundation of understandable code, popular design patterns were used in different components of the system. They are as follows (in the current build): 
* Singleton Pattern for the Spotify Profile Access and Playback functionality
* Abstract Factory Pattern for creating collaborative playlists
* Observer Pattern for notifying the playlist view when the database updates

### Support or Contact
Having trouble with In Rotation? Check out the [project repo](https://github.com/cpe305/fall2016-project-AndrewCofano) or contact support at **acofano@calpoly.edu** to give you a hand.



