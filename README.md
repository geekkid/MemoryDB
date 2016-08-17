# MemoryDB
This is in response to an interview test I had recently. This is a simple implementation of a memory database with nested transactions


# Overview

The ShoCard SDK for iOS provides methods and data structures required to interact with the ShoCard platform. (the ShoCard platform description)

An application developer instantiates an object of the ShoCardService class and interacts with the methods exposed by that class. The ShoCard SDK defines other data types that are used to interact with the ShoCardService class. This document explains the ShoCardService class, the supporting classes, and the methods exposed by the ShoCardService class.

Document organization

* The [Data Types](#heading=h.nsgcy2oe38a7) section describes all the classes defined by the ShoCard SDK.

* The [SDK methods](#heading=h.jheyexfken85) section describes the high-level methods of the ShoCardService class. These methods provide a high level abstraction to the methods exposed by the ShoCard platform. Further in the document there is a reference to all the low-level methods that can be accessed from the ShoCardService class allowing the SDK developer a more detailed access to the workings of the SDK. Most developers will use the high-level SDK methods in their apps and use the lower level methods for debugging, auditing and investigative purposes.

* The [Sample code](#heading=h.2z95yniwcnbd) section provides some code samples and explain a simple use cases in which the SDK can be used. The code sample can be found in our github repository.

* The [Low-level methods](#heading=h.pqpkhykwocqj) section digs deeper into the ShoCard SDK methods. This section breaks down the high-level methods into individual calls made by the SDK. This section is useful for learning more about the ShoCard SDK and platform.

# Data Types

## ShoCardID

This is the primary means of identifying an entity in the ShoCard platform. Any communication with the ShoCard platform via the ShoCard services requires that the application developer provides the ShoCardIDs of the various actors in the interaction. As an example, if the application needs to share data with another ShoCard App, the application instantiates the ShoCardService object with the device specific ShoCardID. To share the data the application then makes the "[shareData](#heading=h.h1o6uj3zfzo9)" call and provides the ShoCardID of the receiving entity and the data that needs to be shared.

---


## Entity

The Entity class encapsulates information related to a given ShoCardID. Among other fields it can be used to get the public RSA key for given ShoCardID. 

* shoCardID: String

* creationTime: Timestamp

* publicKey: String

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td><code>class Entity {
    
var shoCardID:String

var creationTime:NSTimeInterval

var publicKey: String

}</code></td>
  </tr>
  <tr>
    <td>Java</td>
    <td>
```swift
    class Entity {  
String shoCardID;  
java.util.Timestamp timestamp;  
String publicKey;  
}
```
</td>
  </tr>
  <tr>
    <td>JSON</td>
    <td>{
"shoCardID" : “string”
“timestamp” : “number”
“publicKey” : “string”
}</td>
  </tr>
</table>


* * *


## ShoCardService

This is the primary object that needs to be instantiated for interacting with the ShoCard SDK. This object provides all of the data structures, the methods, and state management you will be using to interact with the ShoCard services. It is the responsibility of the app using the SDK to store the device specific ShoCardID.

A new ShoCardID can be created by instantiating the ShoCardService object without a ShoCardID:

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>ShoCardService(shoCard: Entity?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>ShoCardService(Entity shoCard)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


A call to getShoCard will return the ShoCard object with the ShoCardID associated with that instance of the SDK.

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func getShoCard(completion:(shoCard:ShoCard?, error:SCError?) -> Void))</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void getShoCard(IShoCardCreated completion)

interface IShoCardCreated {
void shoCardCreated(ShoCard shoCard, SCError error);
}</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


* * *
SaltedData

A salted data object contains content and an associated "salt" used to make the storage of the string cryptographically secure. ([https://en.wikipedia.org/wiki/Salt_(cryptography)](https://en.wikipedia.org/wiki/Salt_(cryptography))[)](https://en.wikipedia.org/wiki/Salt_(cryptography))

* content:String

* salt:String

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>class SaltedData {
var content:String
var salt:String
}</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>class SaltedData {
String content;
String salt;
}</td>
  </tr>
  <tr>
    <td>JSON</td>
    <td>{
"content" : “string”
“salt” : “string”
}</td>
  </tr>
</table>


# * * *


## Certification

* certificationID:String

* createDate:Timestamp

* certifier:SaltedData

* certifiee:SaltedData

* referenceCertificationID (optional): SaltedData (used to reference a another certification which provided the original data)

* certifiedData: Dictionary/Map of SaltedData key to SaltedData value

Note: The JSON representation of the Certification object differs slightly since dictionary keys are represented by strings in JSON.

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>class Certification {
var certificationID:String
var createDate:Timestamp
var certifier:SaltedData
var certifiee:SaltedData
var referenceCertificationID:SaltedData
var certifiedData:[SaltedData:SaltedData]
}</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>class SaltedData {
String certificationID;
java.util.Timestamp createDate;
SaltedData certifier;
SaltedData certifiee;
SaltedData referenceCertificationID;
Map<SaltedData, SaltedData> certifiedData;
}</td>
  </tr>
  <tr>
    <td>JSON</td>
    <td>{
"certificationID" : “string”,
“createDate” : “long”,
“certifier” : {
“content” : “string”,
“salt” : “string”
},
“certifiee” : {
“content” : “string”,
“salt” : “string”
},
“referenceCertificationID” : {
“content” : “string”,
“salt” : “string”
},
“certifiiedData” : {
“key” : {
“keysalt” : “string”,
“value” : “string”,
“valuesalt” : “string”
}
}
}</td>
  </tr>
</table>


## * * *


## Share

A share object encapsulates the data and the associated certification to verify the data. The SDK user may choose to share only data without any certification in which case the receiver will not be able to perform any verification on the data provided.

* data: Dictionary/Map of String key to String value

* certifications(optional): Array of Certification

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>class Share {
var data:[String, String]
var certifications:[Certification]?
}</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>class Share {
Map<String, String> data;
Array<Certification> certifications;
}</td>
  </tr>
  <tr>
    <td>JSON</td>
    <td>{
"data" : { “string” : “string” },
“certifications” : [
“<Certification JSON>”
]
}</td>
  </tr>
</table>


# SDK methods

## Overview

1. Share Data

2. Self Certify Data

3. Certify Data

4. Unsolicited Certify Data

5. Verify Certification

6. Request Share

## Language specific traits

Most of the iOS and Android ShoCard SDK methods require asynchronous completion block parameters. This is to ensure that the networking methods executed by the ShoCard SDK do not block the main thread which is responsible for handling UI interactions, among other things. For this purpose Java provides [caller side lambda expressions](http://www.oracle.com/technetwork/articles/java/architect-lambdas-part1-2080972.html) and Swift provides [closure blocks](https://developer.apple.com/library/ios/documentation/Swift/Conceptual/Swift_Programming_Language/Closures.html). For Java the parameters are defined as functional interfaces. The functional interface definitions documented alongside the methods definitions below. Please refer to the language specific documentation for more details. This document provides  some sample code but a detailed explanation is beyond the scope of this document.

## Note

All methods use the ShoCardID that was used to instantiate the ShoCardService object as one of the entities in the communication. The second ShoCard entity is specifically mentioned in each method definition.

## Share Data

The Share Data method is intended to send data from one ShoCard entity to another trusted ShoCard entity.  

Parameters:

1. recipient:Entity

2. sessionID:String - *A sessionID is either created by the caller or is passed on from an earlier call*

3. data: Array of [Share](#heading=h.mpz6fe5l80oi) objects  - *A **Share** object groups data and associated certifications. One call to this SDK function can supply multiple instances of the Share ob**ject.*

4. completion block: The SDK calls the completion block when the share operationcompletes. If there was an error sharing data the completion block will supply a non-null error parameter.

Return: Void

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func shareData(recipient:Entity, sessionId:String?, data:[Share], completion:(error:SCError?) -> Void)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void shareData(Entity recipient, String sessionId, Array<Share> data, IShareDataCompleted completion)

interface IShareDataCompleted {
void shareDataCompleted(SCError error);
}</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## * * *
Self-certify Data

The Self-certify Data SDK method creates a self-certification of any personally identifiable information (PII). This call is intended to create a proof on behalf of the user that they own the data and have certified it for later verification and certification by a third party.

Parameters:

1. data: Dictionary/Map of String key to String value

2. completion block: The result of a self-certify call is a Certification object. The completion block provides the Certification object upon successful creation of the a certification. The completion block will supply a non-null error parameter if the operation results in an error.

Return: Void

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func selfCertifyData(data:[String:String], completion:(certification:Certification?, error:SCError?) -> Void)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void selfCertifyData(Map<String, String> data, ICertificationsCreated completion)

interface ICertificationsCreated {
void certificationsCreated(Certification certification, SCError error);
}
</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## * * *
Certify Data

The certify method is called to create a certification of previously shared data. This method, therefore, references a certification which was shared in the ["Share Data"](#heading=h.h1o6uj3zfzo9) call by the sender. The certification is created by the ShoCard entity instantiating the ShoCardService object. The ShoCard entity receiving the certification is passed in the parameter *certifiee*. When certifying data this method expects the caller to provide a reference certification based for additional verification. See examples below for more information.

Note: When the caller does not intend to provide a reference certification the ["Unsolicited Certify Data"](#heading=h.pqh7gnkcgzio) method should be called.

	Parameters:

1. certifiee:Entity

2. referenceCertification: Certification

3. sessionID(optional):String - *A sessionID supplied here must be passed on from an earlier call*

4. data: Dictionary/Map of String key to String value

Return: Certification

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func certifyData(certifiee:Entity, sessionId:String?, referenceCertification: Certification, data:[String:String], completion:(certification:Certification?, error:SCError?) -> Void))</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void certifyData(Entity certifiee, String sessionId, Certification referenceCertification, Map<String, String> data, ICertificationCreated completion)

interface ICertificationCreated {
void certificationCreated(Certification certification, SCError error);
}</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


* * *


## Unsolicited Certify Data

The unsolicited certify method is called to create a certification with data. This method is used to create a certification and share certified data with the ShoCard entity receiving the certification. The certification is created by the ShoCard entity instantiating the ShoCardService object. The ShoCard entity receiving the certification is passed in the parameter *certifiee*. The ShoCard Entity receiving the certification (*certifiee*) is notified by the ShoCard platform using the ["Certification Received"](#heading=h.gzc57dcstvlk) notification.

	Parameters:

1. certifiee:Entity

2. sessionID(optional):String - *A sessionID supplied here must be passed on from an earlier call*

3. data: [String:String] - *A user can supply multiple **Share** to the certify data SDK function. This will result in multiple Certification objects being returned from this call*

Return: [Certification] - *There will be one certification object returned per **Share** provided. The SDK user is required to store the Certification objects*

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func unsolicitedCertifyData(certifiee:Entity, sessionId:String?, data:[String:String], completion:(certifications:[Certifications]?, error:SCError?) -> Void))</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void unsolicitedCertifyData(Entity certifiee, String sessionId, Map<String, String> data, ICertificationsCreated completion)

interface ICertificationsCreated {
void certificationsCreated(Array<Certification> certifications, SCError error);
}</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## Verify Data

	Parameters:

1. verifiee:Entity - *The data will be verified for this ShoCard*

2. data: [Share]

3. completion block

	Return:

1. Bool - *This SDK function returns true or false. More detailed SDK **methods** are provided to explore individual verification results of the **Share** *

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func verifyData(verifiee:Entity, data:[Share], completion:(isSuccessful:Bool?, error:SCError?) -> Void)) -> Bool</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>Bool verifyData(Entity verifiee, Array<Share> data, IVerificationResult completion)

interface IVerificationResult {
void dataVerificationComplete(Boolean isSuccessful, SCError error);
})</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## Request Share

	Parameters:

1. toShoCard:Entity

2. message(Optional): String

3. sessionID: String - *The caller to this API is responsible for creating the session.*

4. requestType: String

5. requestedKeys (Optional): [String]

6. expiration: Int - *Number of seconds for which this request is valid. The SDK receiving this request is responsible to check if the request has not expired before calling the shareData SDK function*

	

	Return: void

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func requestShare(toShoCard:Entity, message:String?, sessionID:String, requestedKeys:[String]?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void requestShare(Entity toShoCard, String message, String sessionID, Array<String> requestedKeys)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## Notification Handling

The ShoCard SDK provides a means to process notifications received from another ShoCard or from the system. When notification is received by the application in a serialized message format that can be passed on to the SDK instance to be processed. The ShoCard SDK instance will automatically process messages generated by the ShoCard platform and call appropriate delegate methods contained in the message. The ShoCard SDK provides one function to process a message received by the application.

### Process Notification

Parameters:

1. notification:Any serialized object - *The notification received from the ShoCard platform. This will be a JSON string in the current version of the SDK**.*

2. notificationHandler(Optional): NotificationHandler - *See below for NotificationHandler delegate definition.*

	Return: void

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func processNotification(notification:String, notificationHandler:NotificationHandler?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void processNotification(String notification, NotificationHandler notificationHandler)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


### Register For Notification

The ShoCard SDK exposes the register for notification function which will allow the SDK developer an option to have one object implementing the NotificationHandler delegate across the system.

Parameters:

1. notificationHandler: NotificationHandler - *See below for NotificationHandler delegate definition.*

	Return: void

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func registerForNotification(notificationHandler:NotificationHandler)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void registerForNotification(NotificationHandler notificationHandler)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


# NotificationHandler Delegate

## Shared Data Received

A ShoCard ID (receiver) can receive a message from another ShoCard ID (sender) to share data some piece of data. This data will be enveloped for the receiver and signed by the sender. A ShoCard Entity 

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func shareReceived(sender:Entity, data:Share)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void shareReceived(Entity sender, Share data)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## Certification Received

This SDK method is called when the notification contains a message related to receiving a certification from another ShoCard entity.

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func certificationReceived(sender:Entity, certification:Certification)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void certificationReceived(Entity sender, Certification certification)</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


## Share Request Received

This SDK method is called when the notificaton contains a message requesting the recipient for 

# Sample Code

# Low-level methods

The ShoCard SDK provides low level apis to interactions with ShoCard service  RESTful APIs. The available resources are, ShoCard, Seal, Certification, and Share.

## Create ShoCard

![image alt text](image_0.png)

## Share Data

![image alt text](image_1.png)

**ShoCard** 

**Attributes**

String shocardid

String token_id

Int64 updated_at

String device_type

String pubc

String pubs

**methods**

Create

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func createShoCardID(completion: ((shocard:ShoCard?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void createShoCard(ShoCardCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Read

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func getShoCard(shocardId:String, completion: ((shocard:ShoCard?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void getShoCard(String shocardId, ShoCardCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Update

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func updateShoCard(deviceToken: NSData?, shocardId: String, completion: ((shocard:ShoCard?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void updateShoCard(String deviceToken, String shocardId, ShoCardCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Delete (N/A)

**Seal** 

**Attributes**

String id

String block_id

Int64 inserted_at

String pubc

String data_string

Map[String,AnyObject data

String signature

String sdata

**methods**

Create

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func createSeal(sealId:String, idcards: [IDCard], completion: ((seal:Seal?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void createSeal(String shocardId, ArrayList<IDCard> idcards, SealCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Read

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func getSeal(sealId:String, completion: ((seal:Seal?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void getSeal(String sealId, SealCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Update (N/A)

Delete (N/A)

**Certification** 

**Attributes**

String pubc

String block_id

String hash

String id

String signature

String dataString

Map[String,AnyObject] data

String shocardid

String canceled_id

String canceled_by_id

Boolean ack

Boolean cancel

**methods**

Create

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func createCertification(certificationRecord: CertificationRecord, completion: ((certificationResponse:CertificationResponse?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void createCertification(CertificationRecord certificationRecord, CertificationResponseCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Read

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func getCertification(certId:String, completion: ((certificationResponse:CertificationResponse?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void getCertification(String certId, CertificationResponseCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Update (N/A)

Delete

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func cancelCertification(shocardid: String, certificationCancelRecord:CertificationCancelRecord, completion: ((certificationResponse:CertificationResponse?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void cancelCertification(String shocardid, CertificationCancelRecord certificationCancelRecord, CertificationResponseCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


**ShoStoreData** 

**Attributes**

String uniqueId

String ttl

String id

String url

String secret

Int64 createTime

**methods**

Create

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func createShoStoreData(uniqueId:String, shocardId_to:String?, shocardId_from:String?, envtype:ShoCardPresenter.EnvelopType, data:ShareData, ttl:String, completion: ((shoStoreData:ShoStoreData?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void createShoStoreData(String uniqueId, String shocardId_to, String shocardId_from, EnvelopType envtype, ShareData data, String ttl, ShoDataCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Read

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func getShoStoreData(url: String, symKey: String?, completion: ((shareData:ShareData?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void getShoStoreData(String url, String symKey, ShareDataCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


Update (N/A)

Delete (N/A)

**Share**

**Attributes**

	ShareData

**methods**

Create

<table>
  <tr>
    <td>Code</td>
    <td></td>
  </tr>
  <tr>
    <td>Swift</td>
    <td>func createShare(shocardid: String, toShocardid:String, shareData: ShareData, completion:((body:[String:AnyObject]?, error:SCError?) -> Void)?)</td>
  </tr>
  <tr>
    <td>Java</td>
    <td>void createShare(String shocardid, String toshocardid, ShareData shareData, ShareCallBack completion);</td>
  </tr>
  <tr>
    <td>REST</td>
    <td></td>
  </tr>
</table>


