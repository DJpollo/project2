# project2
4/11/2025
7:37pm
after much work done i forgot that i had to ass git implementation. so i will try and recap the process so far and where i am in my progress. 
the threaddemo java file given as an example was where i beggan and i used tht and modified it to help me out.
i had dificulty understanding how semaphores worked but with a little bit of practice i have it working now.
ive made a class for the main threads i will use with the modified run method.i started with simple interactions between my threads such as saying hi and saying something back in return. once i acomplished that i could do bigger things such as the teller understanding what the customer wants to do. now i am where i am curently. 

curently i am sucesfully having the customer interacting with the teller.if the customer has decided if he wants to withdraw then i call the manager which is also ran using semahores. now the only dificulty i am having is if im doing this corectly or not. curently when ran one thread is ran one by one. but in the sample run given the threads bounce around and arent finishing one by one. curently the way i have evrything set up the teller thread is done running once it is completly done interacting with the current customer. 

i am going to try and see how i can change this or at the very least fix my output. 

9:25pm
ive gotten the input to look more like the example given.but now ive noticed the output doesnt run very realistic. expesially the selecting teller promt. once it is selected the interaction should continue but other thread outputs cut it off and ruins the continuity. Customer 2 : Getting in line
Customer 3 : Entering the bank
Customer 3 : Getting in line
Customer 3 : Selecting teller
Customer 0 : Getting in line
Customer 0 : Selecting teller
Customer 4 : Getting in line
Customer 4 : Selecting teller
Customer 2 : Selecting teller
im trying to make it more realistic to where once it select the teller it should show the interaction being done.

9:36pm 
done with this session for the day. another thing that arose was the withdraw action. because only one teller can talk to the manager at a time i will need to implement it where if the manager has been called it should wait. curently i have it if two withdraws are called upon then they both go to the manager. also each transaction happens at the same time 
Teller 1 ok customer you want to withdraw getting the manager
Teller 0 ok customer you want to deposit
Teller 2 ok customer you want to deposit
so if 2 where to withdraw then this would cause trouble with the manager.
going to have to fix this. how to make it so each strasaction is done while not getting in the way with the others? i will have to figure this out tommorow.

4/12/2025
8:07pm
in  this session im going to add the final touches and try and fix the problems i mentioned before.
9:35pm
after a few tries ive got most of it working. ive created the manager a thread class alowing me to simulate him working on helping getting the money. the output now also makes more sense now. going to fix the putput more througly and will add a few more missing features.
10:45pm
i believe i am done but the only thing im having trouble on is letting the teller know there are no more customers and to go home. once i solve this i will be done. maybe a global counter. this would defenetaly solve my problem but feel like its not the right way to do it. my queue after every run is empty due to polling the customer as soon as its added. 

4/13/2025
6:53pm 
tried mnay options yesterday but ultimetly ive decided to have a global counter set at 50 customers. everything works as it should now and now im just going to add comments in  my code to explain how evrthing works and checking to make sure the specifications are met.
8:07pm
done
