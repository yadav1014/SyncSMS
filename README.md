# SyncSMS

Architecture followed : MVVM  
Language :Kotlin  
minimum version supported : 14  
Java version: 8
  
External libraries:  
-Koin for dependency injection  
-Retrofit for network calls  (Not used as there is no api)  
-OkHttp as HttpClient  
-Coroutines for handling concurrency  
-Recyclerview   
-WorkManager for background job scheduling  
-RoomDb for local storage

Following image shows the project structure  
  
![alt text](https://raw.githubusercontent.com/yadav1014/SyncSMS/master/SyncSms.png)  
  


App uses WorkManager for pushing data to server in the background.   
Proposed Contract for API call  
POST with body array of 'Sms' object.   
Following is the Sms class  
```
class Sms {
   var uid: Long = 0
   val amount: String,
   val date: Long,
   var synced: Boolean
}
```
