### ABSTRACT ###
**Smart phones, which exist with most users driving along the roads, can be used to monitor road and traffic conditions along their drive, and convey this information to a central server for aggregation and reporting. The information gathered can be used in many ways. One way is to reflect this information on a map accessible by all users. The main idea of this project is thus to use sensed data that mobile phones can provide such as location, directions and others for traffic monitoring.**

In this project, the it was required to develop a mobile phone application that would periodically send sensed data like position and direction, to a central server. The server, then, uses this information to match user's position on a map and updates road segment condition accordingly.

### DELIVERABLES ###
**- Client Side:**
We will be exploiting Google’s [android](http://developer.android.com/index.html) OS capabilities in order to make an android app that is capable of collecting traffic-related vital data such as direction, velocity and GPS position periodically then sending such data frequently to a central server in dynamic intervals of time.

**- Server Side:**
The Server should be able to constantly listen to incoming data from the android client(s) and filter out the data in a smart way. The server will be also storing such data in a database that is constantly updated such that, I can extract the data and form traffic statistics related to a certain road/segment. Finally, the server should be able to reflect the sent data on [Google Maps](http://maps.google.com/) such that the traffic condition can be reflected on the map in real time.

### REQUIREMENTS ###
- The client app should be smart enough to dynamically adjust the frequency of collecting the data.

- The client app should also be smart enough to adjust the frequency of sending data to the Central server.

- The client app should reach a reasonable balance between computation and power
consumption.

- The Server should be smart enough to filter out the incoming data according to its semantics such as cases when incoming redundant data from same location and take into consideration two way roads.

- The Server should be able to formulate and display traffic statistics using the given stored data.

- The Sever should be able to reflect the data sent by the client(s) to the map in real time or as soon as possible.