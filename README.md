# rpsapp
A Rock Paper Scissors Android Studio game made in OpenCV

The Original repo: https://bitbucket.org/rockpaperscissorsapp/rpsapp/src/master/

This game does not work fully but does the basics that get it to work.

Drawbacks:
The game can only be played in accurate light depending on how many contours the program can count off your hand.

There is no mask so the background can be read as well.

Things that should have been done:

Take a skin sample and then play the game (do something to mask the background and identify the hand)

Problems so far:
Since there is no accurate mask, it uses a timer for you to pick what hand you want to use (the timer works by how many hand reads it gets, the program averages at about 5 secs for the limit that is placed).

After the hand is read, the program crashes when trying to go to the next activity
