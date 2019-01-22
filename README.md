ANDROID PROJECT
-
MUSIC SHEET
GENERATION APP
Written By:
Kacper Leszczyński
Youssef Ibrahim
Project description
The project is an Android application with the intent of practicing and improving music reading
abilities in aspiring musicians, especially targeting pianists. Improving reading abilities is most
effectively done through everyday reading of ​new material​. Completely fresh pieces may be
difficult to come by after some time. This is the motivation behind the generation of new music in
just a few clicks. The app consists of two parts:

Sheet music generator ​ - Allows the user to choose one of nine types of pieces to be
generated and displayed in order to practice reading the chosen style of music. The
generated pieces may be saved and later retrieved if the player wishes so. Composers
may also find randomly generated melodies useful inspiration. The types of pieces and
their purposes are:
a. Simple Unison​ - a slow and simple melody played identically in both hands if the
reader is not yet used to reading two staves.
b. Simple Melody​ - introduces independence of hands, while still remaining fairly
easy to read through.
c. Ternary​ - a simple structured piece in ABA form with the intent of training the
reader to remember music, as real pieces often make use of repetition and
variation.
d. Rondo​ - a more extreme idea of the previous type. It’s in ABACAB’A form and
trains not only noticing repetition, but variation (here in the form of transposition).
e. Ornament Ternary​ - just like ternary, but includes additional markings such as
dynamics (crescendos, sforzando), articulation (staccato, accents), and
ornamentation (mordents, trills, turns).
f. Ornament Rondo​ - like above, but more difficult.
g. Scales​ - helps train a fundamental part of music. The melody changes only by
step, so all notes are next to each other, unless the range ends.
h. Perpetual motion​ - A nonstop onslaught of sixteenth notes ending with both
hands executing a different melody in such style. Inspired by some works of
Charles-Valentin Alkan.
i. Random​ - each measure is in a different key and random ornaments are added.
Meant as training without pattern.
Music quiz ​ - Helps reinforce musical skills outside the instrument. An integral part of
reading music is recognizing patterns including intervals and chords, as well as utilizing
music theory. Music isn’t random noise and usually follows some rules (or more
accurately, guidelines). The quiz is divided into multiple categories. Each category has a
set of questions related to the topic. There is also a scoring system that is saved in the
database. Rankings can also be viewed globally across all users. The categories include
a. Note Recognition​ - trains the reader to know where notes lie on a staff in both
treble and bass clefs and well as in different keys.
b. Interval Recognition​ - the reader must identify the distance between two notes in
both clefs and different keys.
c. Chord Recognition​ - the reader should also know shapes of chords and identify
their inversion in various keys in both clefs.
d. General Theory​ - musicians should have a good grasp on notions of music theory
in order to help them understand what and why they are playing. This includes
questions from identifying key signatures to the meanings of different symbols on
sheet music.
Users can register in order to have usernames for rankings. The project also uses an external
Firebase storage as a database that contains:

User data
Player scores
Quiz questions
Saved pieces
Images
ABCJS library
Displaying sheet music
The first idea of converting the piece structure into visual sheet music was by outputting a MIDI
file and then using a third-party library to convert that to sheet music in PDF format. After
implementing the output to MIDI, extensive research showed that such a solution was either
impossible or required breaking apart and modifying pieces of such libraries.
Fortunately, there exists another format of representing music: ABC notation. WHile MIDI
focuses on producing audio output, ABC was created to store visual notation digitally with
support for various sheet music elements that MIDI doesn’t store. Another bonus is that ABC
notation is significantly simpler to translate to than MIDI, which is a confusing and old format
with tons of audio features that make reading MIDIs especially difficult.
The simplest option to translate ABC notation into sheet music turned out to be through a
JavaScript library in a browser called ABCJS. It does it through one function:
ABCJS.renderAbc(id, ABCstring)​ where​ id​ is the id of the DOM element where the
music should be displayed, and ​ABCstring​ is the string of the ABC notation of the music.
The final solution is converting the piece structure into a string of the ABC representation and
generating an HTML file that calls the render function on the generated string. Finally, the app
opens the file in a browser window, obtaining the ABCJS library from the database. This
produces the sheet music that can be read by the user.
Phases of development

Initial documentation, prospective
Basic prototype for the expected application
MVC design
Revised MVC design
MIDI generation
MIDI generation added parameters
Initial UI design
UI design modified
Merging of branches
10.Fixing merging conflicts and emerging errors
11.Populating the Database
12.Testing against multiple platforms
13.Adding extra features
14.Fixing more bugs
15.Final version of application
As the development went on, the application was regularly reviewed by the client (the course
coordinator), and each review was considered as a retrospective sprint as the ​ Agile
methodology was followed along the project’s development. The weekly sprints and reviews
stressed upon multiple issues with the app and some modes and features had to be completely
redesign to meet the actual requirements of the project.
Screenshots
Basically, the application starts with a simply activity for sign in/sign up forms as shown in the

figure_1. The ​ signIn ​functionality is the expected; the email and password are used to retrieve
the user’s shared preferences, state and data to the app form the remote database (Firebase).

For figure_2, the screenshot shows the ​ AlertDialog ​ for ​ signUp. ​The ​ signUp ​form has the proper
validations for the username, the password and the email. Upon success, the user is then saved
to the database.

Figure_3 represents the ​ Home ​ view or the first fragment that appears upon signing in. the initial
and basic fragment is that of categories for quizzes. As shown, there are 6 categories and the
user may click and choose whichever category they prefer to enter the quiz mode (explained

later). Also, the ​ bottom_navigation_bar ​is self-explanatory. It displays the four modes of the
application which are, practicing, categories (quizzes), rankings and finally the browse saved
mode.

Here, the above figures are for the quiz mode AKA categories.
For figure_1, upon choosing a category and clicking on the start button, this page appears and it
represents the quiz. The question may be a text or an image. The 0 on the left is the score and
the 1/15 represent the questions and the progress. The question has 4 choices from which the
user may choose the correct answer.
For figure_2, and after finishing all the questions, the score is displayed along with the number

of correctly answered questions. The user may now click on the ​ Home ​button to go back to the
the ​ Home Activity. ​The score is registered for the user and written in the database for further
processing in the ​ Rankings ​ mode.
The above figures represent the practice mode. This is basically the major mode around which
the whole app revloves. As described earlier, the application generates a random sheet music
upon request for the user and the user chooses the category for the generated sheet.

Figure_1 shows the ​ AlertDialog ​ that first appears in this mode. It shows the 9 options for the
music sheet generation, 8 essential types and 1 ​ Random ​type at the bottom.
Figure_2 and figure_3 show the sheet generated upon choosing the type in the 2 orientations.

In this mode, the app is designed to hide the ​ Bottom_navigation_bar ​while scrolling in both
orientations to give the user a better experience and a clearer view of the sheet music. The

Save ​button is used by the user to save this current music sheet for later access in the ​ Browse
mode.

This set of screenshots belong to the ​ Rankings ​mode.
Figure_1 shows the app users with their usernames and their total scores. The app fetched
such data from the remote database and displays each user’s score sorted descendingly. The
user may click on any user to display further informations.
Figure_2 shows the full description for the chosen score. This page simply displays how the
user acquired the score. The app displays the score for each category chosen by this user and
their total is the score in the previous layout.

The final set of screenshots belong to the ​ Browse ​mode. As previously mentioned, this mode is
based on the ​ Practice ​mode. In the later mode, the user may save the currently generated
music sheet and this mode basically displays all saved sheets in a list.
The first screenshot shows the list with the sheets saved by the current user. The user may click
on any element of the list to display its music sheet.
The second screenshot shows the displayed music sheet that was saved earlier. The layout and

functionality resembles that of the ​ Practice ​mode mostly.
Division of roles
The workload was split between us depending on the features being implemented.
Kacper mostly worked on music generation and the quiz content.
Youssef built and unified the app in Android Studio, building the entire quiz engine, design, and
functionality.
The work was done independently and later combined into the final product. Due to the design
of the application, merging the work was relatively simple with only few issues regarding loading
the ABCJS library.
Active feedback was received by presenting the project at various stages of completion.
Music Generator
The music generator is responsible for the generation of musical compositions and their
conversion into ABC notation in order to be visually presented.
The most basic unit of music here is the note. Next, a NoteGroup contains a collection of notes
in order to produce multiple notes at once. A Measure stores a series of NoteGroups in order to
hold a theme.
Motifs make heavy use of measures in order to store themes. Chords are a general description
of a chord (what type and what octave is takes place) and can be used to generate a random
form form of it. It fulfills the harmonic function of the piece and is tightly related to the melody at
that time.
Finally, a Piece sits at the top of the hierarchy and combines the motifs into a piece that can be
then converted to ABC notation.
Various enumerators help organize the information about notes and such.

The music generator was first made to output only ternary and rondo forms. However, with
some tweaks to the motif generation and some additional functionalities and generalisations,
output for multiple types of compositions was allowed. Nine were made use of in total that
display its various capabilities.
Motifs generate a melody by first randomly dividing the rhythm and then placing melody notes
with that rhythm. Chord changes are based on harmonic rhythm and the current degree of the
melody. Chords come in three types:

All three notes played simultaneously for the duration of the harmonic rhythm
The root of the chord followed by the remaining two pitches, each half the harmonic
rhythm duration.
The chord is split into four notes played as root-third-fifth-third.
Measures can be transposed by a specified number of pitches (which changes the key the
notes are played) and the changes propagate downwards through NoteGroups into Notes.
Chords may also undergo transposition.
UML Case Diagram

Basic Mockup

Technologies Used
The application’s simplicity reflects the technologies used during development. Regarding user
interface design and front-end development the following technologies were used:

Google’s Firebase Realtime Database:
a. The first prototype of the application had SQLite database technology but as the
requirements drove the change towards a Realtime Remote Database, Firebase
was the plausible solution. The Firebase Realtime Database is a cloud-hosted
database. Data is stored as JSON and synchronized in realtime to every
connected client. All clients share one Realtime Database instance and
automatically receive updates with the newest data. Firebase simplifies security
measures allowing secure access to the database directly from client-side code.
Data is persisted locally, and even while offline, realtime events continue to fire,
giving the end user a responsive experience. The Realtime Database is a NoSQL
database and as such has different optimizations and functionality compared to a
relational database.
Google’s Cloud Storage:
a. As the direction went towards Firebase Realtime Database, it only seemed
obvious to merge such technology with another Google technology which is
Cloud Storage for Firebase. Cloud Storage for Firebase is a powerful, simple,
and cost-effective object storage service built for Google scale. The Firebase
SDKs for Cloud Storage add Google security to file uploads and downloads for
your Firebase apps, regardless of network quality. The main use of this
technology is to upload the saved HTML file format music sheet notes to the
cloud and link them with their appropriate client allowing later fetching to be
simple and consistent. The technology is also used to store the questions and the
categories along with their respective JPG files format.
Picasso libraries:
a. As the application is mainly based on images and HTML file for context and
visual flair, Picasso allows for hassle-free image loading and caching.
Those were the major technologies used in the application. However, the application found use
of more technologies for app-bundles, plugins, code generation techniques, layout
management, debugging and testing.
Problems encountered
Along development, the application went through a lot of failures due to different factors. As

mentioned earlier, developers agreed on following the ​ Agile ​methodology for project
management since it seemed reasonable as regular reports were issued weekly along with
feedback and possible modifications.

The first basic prototype of the app had a lot of bugs including the local database
technology which slowed the runtime of the application, the bad architecture for the MVC
design precisely the ​ Questions ​model.
The UI had to change multiple times to meet the requirements as also it was not
interactive enough and ill-designed.
One of the major issues encountered during development was the injection of the ​ abc.js
JS file inside the ​ music.html ​ HTML file. As the browsing feature implies, the saved
music sheets should be saved on the cloud for later fetching during ​ Browsing ​mode. In
order to achieve this, the initially generated HTML file is saved temporarily locally on the
device’s external storage and if the ​ save ​event is fired, the file is uploaded to the cloud
with a call back function returning the file’s URL to be later linked to the issuing client.
Another major issue was saving the HTML file locally on the device running the app
without the need for permissions to reduce the application’s complexity for the average
user. The solution was to save the file on the external storage since almost all new
devices contain external storage.
The application also had a huge breakdown while tested on multiple platforms as some
devices did not behave as expected when running the app. Therefore, the application
was thoroughly tested to support the most common API levels, Android target devices
and ​ Pie, Oreo ​ based devices.
Some bugs were also related to the outdated documentation of some technologies such
as Firebase Authentication.
In conclusion, the app last version that is being delivered behaves as described in the initial
documentation with all the features designed and some extra features were also added to
simplify the apps interaction with the users.
