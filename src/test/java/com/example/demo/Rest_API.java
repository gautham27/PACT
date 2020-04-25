//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.http.Header;
//import io.restassured.http.Headers;
//import io.restassured.path.json.JsonPath;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//import java.awt.AWTException;
//import java.awt.HeadlessException;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Hashtable;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import static io.restassured.RestAssured.*;
//
//public class Rest_API {
//
//
//	private static Hashtable<String,Hashtable<String,String>> h2APIPrefs=new Hashtable<String,Hashtable<String,String>>();
//	private static Hashtable<String,String> hLastResponse=new Hashtable<String,String>();
//	private static Hashtable<String,LinkedList<String>> hHeaderParams=new Hashtable<String,LinkedList<String>>();
//	private static Hashtable<String,RequestSpecification> hLastRequestObject=new Hashtable<String,RequestSpecification>();
//
//	public static void raSetAPI_Preferences(String sTestName,String sPrefName,String sPrefVal) throws HeadlessException, IOException, AWTException, InterruptedException
//	{
//		Hashtable <String, String > hPref = new Hashtable<>();
//		hPref.put(sPrefName, sPrefVal);
//
//		String sDesc = Logs.log(sTestName);
//
//		try {
//
//			if (h2APIPrefs.containsKey(sTestName))
//				h2APIPrefs.get(sTestName).put(sPrefName, sPrefVal);
//			else {
//				h2APIPrefs.put(sTestName,hPref);
//			}
//
//			Reporter.print(sTestName, sDesc + "\n API Preference :" + sPrefName + " Set  :: Done");
//
//	} catch (Exception ex) {
//		if (Utils.handleIntermediateIssue()) { raSetAPI_Preferences(sTestName,sPrefName,sPrefVal);}
//		Reporter.printError(sTestName, ex, sDesc, "");
//	}
//	}
//
//	public static void raStore_Response(String sTestName,String sVar) throws InterruptedException, HeadlessException, IOException, AWTException
//	{
//		String sDesc = Logs.log(sTestName);
//		try {
//			Utils.setScriptParams(sTestName, sVar, hLastResponse.get(sTestName));
//			Reporter.print(sTestName, sDesc +" RESPONSE STORED IN  : "+sVar+ " :: Done");
//		}
//		catch(Exception e) {
//			if (Utils.handleIntermediateIssue()) { raStore_Response(sTestName,sVar);}
//			Reporter.printError(sTestName, e, sDesc, "");
//		}
//	}
//
//	public static void raStore_Response_in_File(String sTestName,String FilePath) throws InterruptedException, UnsupportedOperationException, IOException, AWTException
//	{
//		String sDesc = Logs.log(sTestName);
//
//		try {
//			File file1=new File(FilePath);
//			if (file1.createNewFile()) {
//				Logs.log(sTestName,"NEW FILE CREATED");
//			}
//			else {
//				Logs.log(sTestName, "REPLACING OLD FILE");
//			}
//			FileWriter writer = new FileWriter(file1);
//			writer.write(hLastResponse.get(sTestName));
//			writer.close();
//			Reporter.print(sTestName, sDesc+" DATA STORED IN :: "+FilePath + " :: Performed");
//		}
//		catch(Exception e) {
//			if (Utils.handleIntermediateIssue()) { raStore_Response_in_File(sTestName,FilePath);}
//			Reporter.printError(sTestName, e, sDesc, "");
//		}
//	}
//
//	public static void raVerifyIn_Response(String sTestName,String criteria_path,String sExpVal) throws InterruptedException, HeadlessException, IOException, ParseException, AWTException	{
//		String sDesc = Logs.log(sTestName),sVal,sActVal="true",expVal = "true";
//		Boolean bStatus=false;	JSONObject jsonobj =null;JsonPath jsonPath =null;JSONArray jsonarr=null;
//
//		try {
//			sVal=sExpVal;
//			sExpVal=	Utils.Helper.validateUserInput(sTestName,sExpVal);
//			sExpVal = Reporter.filterUserInput(sExpVal);
//
//			if (h2APIPrefs.get(sTestName).get("App_Type").equalsIgnoreCase("Text")) {
//					if (hLastResponse.get(sTestName).contains(sExpVal)) {
//						bStatus=true;
//						sDesc=sDesc+" ::  RESPONSE HAS THE EXPECTED VALUE : " + sExpVal;
//						sActVal = "true";
//					}
//					else
//						{
//						sDesc=sDesc+" ::  RESPONSE DOES NOT HAVE THE EXPECTED VALUE : " + sExpVal;
//						sActVal = "false";
//						}
//
//			}
//			else {
//				try {
//					if (hLastResponse.get(sTestName).charAt(0)=='[') {
//						jsonarr=(JSONArray) new JSONParser().parse(hLastResponse.get(sTestName));
//						jsonPath = new JsonPath(jsonarr.toJSONString());
//					}
//					else {
//						jsonobj = (JSONObject)  new JSONParser().parse(hLastResponse.get(sTestName));
//						jsonPath = new JsonPath(jsonobj.toJSONString());
//					}
//					if ((jsonPath.get(criteria_path)).toString().contains(sExpVal)) {
//						sDesc = sDesc + "\n*** THE VALUE FOR GIVEN " + criteria_path + " IS  PRESENT IN THE RESPONSE :  *** :: Performed";
//						bStatus=true;
//						sActVal = "true";
//					}
//					else {
//						sDesc =  sDesc + "\n*** THE VALUE FOR GIVEN " + criteria_path + " IS NOT PRESENT IN THE RESPONSE :  *** :: Performed";
//						//sActVal = "false";
//					}
//				}
//				catch(Exception e) {
//					Reporter.print(sTestName,  sDesc + "\n*** RESPONSE HAS SOME ERRORS CANNOT PARSE IT  ::  "+ e + "*** :: Performed");
//				}
//			}
//			Reporter.print(sTestName, sVal, sDesc, expVal, sActVal, bStatus);
//			}
//		catch(Exception e)
//		{
//			if (Utils.handleIntermediateIssue()) { raVerifyIn_Response(sTestName,criteria_path,sExpVal); }
//			Reporter.printError(sTestName, e, sDesc);
//		}
//	}
//
//	public static void raGet_Request(String sTestName,String Req_URL) throws InterruptedException, HeadlessException, IOException, AWTException {
//
//		int code=0;	String response_content=null,sDesc=null;Response Resp;
//		Helper.check_Prefs(sTestName);
//		sDesc = Logs.log(sTestName);
//
//		try {
//			if (h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA")) {
//				Resp=given().when().get(Req_URL);
//			}
//			else {
//				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).when().get(Req_URL);
//			}
//
//			response_content=Resp.thenReturn().body().asString();
//
//			hLastResponse.put(sTestName, response_content);
//			code=Resp.getStatusCode();
//
//
//			if (code==200) {
//				Logs.log(sTestName, " GET REQUEST SUCCESSFULL, Response is : \n" + hLastResponse.get(sTestName) +"\n");
//				sDesc=sDesc+ " GET REQUEST SUCCESSFULL";
//			}
//			else {
//	    		Logs.log(sTestName, " GET REQUEST FAILED");
//	    		sDesc=sDesc+ " GET REQUEST FAILED";
//			}
//			Reporter.print(sTestName, sDesc + "  :: Performed");
//		}
//		catch(Exception e) {
//
//			if (Utils.handleIntermediateIssue()) { raGet_Request(sTestName,Req_URL); }
//			Reporter.printError(sTestName, e, sDesc);
//		}
//
//	}
//
//
//	public static void raDelete_Request(String sTestName,String Req_URL) throws InterruptedException, HeadlessException, IOException, AWTException {	String sDesc=null; 	Response Resp=null;	int status_code;
//
//		sDesc = Logs.log(sTestName);
//		Helper.check_Prefs(sTestName);
//		try {
//			if (h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA"))
//		   	 	Resp=given().when().delete(Req_URL);
//			else
//				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).when().delete(Req_URL);
//
//			 status_code=Resp.getStatusCode();
//			 hLastResponse.put(sTestName, Resp.thenReturn().body().asString());
//		   	 if (status_code==200)
//		   		sDesc=sDesc+ " DELETED SUCCESSFULLY";
//		   	 else
//		   		sDesc=sDesc+ " NOT DELETED SUCCESSFULLY";
//
//		   	 Reporter.print(sTestName, sDesc + "  :: Performed");
//
//		}
//		catch(Exception e)
//		{
//			sDesc=sDesc+  " ERROR IN DELETE REQUEST";
//			if (Utils.handleIntermediateIssue()) { raDelete_Request(sTestName,Req_URL); }
//			Reporter.printError(sTestName, e, sDesc);
//		}
//	}
//
//	public static void raPost_Request(String sTestName,String Req_URL,String FilePath) throws InterruptedException, HeadlessException, IOException, AWTException {
//		String sDesc=null,data;	int status_code=0;	Response Resp=null;
//		sDesc = Logs.log(sTestName);
//		Helper.check_Prefs(sTestName);
//		FilePath=Utils.Helper.validateUserInput(sTestName, FilePath);
//
//		if(FilePath.charAt(1)==':')
//			data=Helper.read_File(FilePath);
//		else
//			data=FilePath;
//	   	 try
//	   	 {
//	   		if (h2APIPrefs.get(sTestName).get("App_Type").equalsIgnoreCase("Text")) {
//
//	   			if (h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA"))
//	   				Resp=given().contentType(ContentType.TEXT).body(data).post(Req_URL);
//
//	   			else
//	   				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).contentType(ContentType.TEXT).body(data).post(Req_URL);
//	   		}
//	   		else {
//		   		 	if (h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA"))
//		   		 		Resp=given().contentType(ContentType.JSON).body(data).when().post(Req_URL);
//		   			else
//		   				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).contentType(ContentType.JSON).body(data).when().post(Req_URL);
//		 	}
//
//	   		hLastResponse.put(sTestName, Resp.thenReturn().body().asString());
//   	 		status_code=Resp.getStatusCode();
//	   		if (status_code==200)
//	   			sDesc=sDesc+ " POST REQUEST SUCCESSFULL";
//	    	else
//	    		sDesc=sDesc+ " NOT POSTED SUCCESSFULLY";
//
//	   		Reporter.print(sTestName, sDesc + "  :: Performed");
//
//	   	}
//	   	 catch(Exception e)
//	   	 {
//	   		if (Utils.handleIntermediateIssue()) { raPost_Request(sTestName,Req_URL,FilePath); }
//	   		Reporter.printError(sTestName, e, sDesc);
//	   	 }
//
//
//	}
//
//	public static void raPut_Request(String sTestName,String Req_URL,String FilePath) throws InterruptedException, HeadlessException, IOException, AWTException
//	{
//		String sDesc=null,data;	Response Resp=null;	int status_code=0;
//
//		sDesc = Logs.log(sTestName);
//		Helper.check_Prefs(sTestName);
//		FilePath=Utils.Helper.validateUserInput(sTestName, FilePath);
//
//		if (FilePath.contains("\\"))
//			data=Helper.read_File(FilePath);
//		else
//			data=FilePath;
//		 try {
//			 if (h2APIPrefs.get(sTestName).get("App_Type").equalsIgnoreCase("Text")) {
//
//			 	if (h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA"))
//	   				Resp=given().contentType(ContentType.TEXT).body(data).put(Req_URL);
//
//			 	else
//	   				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).contentType(ContentType.TEXT).body(data).put(Req_URL);
//	   		}
//		   	 else {
//		   			if(h2APIPrefs.get(sTestName).get("Auth").equalsIgnoreCase("NA"))
//						Resp=given().contentType(ContentType.JSON).body(data).when().put(Req_URL);
//		   			else
//		   				Resp=given().auth().preemptive().basic(h2APIPrefs.get(sTestName).get("Auth_Username"), h2APIPrefs.get(sTestName).get("Auth_Password")).contentType(ContentType.JSON).body(data).when().put(Req_URL);
//
//		   	}
//
//	   		hLastResponse.put(sTestName, Resp.thenReturn().body().asString());
//   	 		status_code=Resp.getStatusCode();
//		   		if(status_code==200)
//		   			sDesc=sDesc+ " PUT REQUEST SUCCESSFULLY EXECUTED";
//		    	else
//		    		sDesc=sDesc+ " PUT REQUEST FAILED";
//
//		   	 Reporter.print(sTestName, sDesc + "  :: Performed");
//	   	}
//	   	 catch(Exception e) {
//	   		sDesc=sDesc+ " ERROR IN READING FILE";
//	   		if (Utils.handleIntermediateIssue()) { raPut_Request(sTestName,Req_URL,FilePath); }
//	   		Reporter.printError(sTestName, e, sDesc);
//	   	 }
//
//	}
//	public static void raAddHeaders(String sTestName, String headerName,
//			String headerValue) throws InterruptedException, HeadlessException,
//			IOException, AWTException {
//		String sDesc = null;
//		RequestSpecification Resp = null;
//
//		sDesc = Logs.log(sTestName);
//		Helper.check_Prefs(sTestName);
//		headerName = Utils.Helper.validateUserInput(sTestName, headerName);
//		headerValue = Utils.Helper.validateUserInput(sTestName, headerValue);
//		try {
//			LinkedList head;
//			if (hHeaderParams.get(sTestName) == null)
//				head = new LinkedList();
//			else
//				head = (LinkedList) hHeaderParams.get(sTestName);
//			head.add(new Header(headerName, headerValue));
//			hHeaderParams.put(sTestName, head);
//			Headers headers = new Headers((List) hHeaderParams.get(sTestName));
//
//			if (((String) ((Hashtable) h2APIPrefs.get(sTestName))
//					.get("App_Type")).equalsIgnoreCase("Text")) {
//				if (((String) ((Hashtable) h2APIPrefs.get(sTestName))
//						.get("Auth")).equalsIgnoreCase("NA")) {
//					Resp = RestAssured.given().contentType(ContentType.TEXT)
//							.headers(headers);
//				} else {
//					Resp = RestAssured
//							.given()
//							.auth()
//							.preemptive()
//							.basic((String) ((Hashtable) h2APIPrefs
//									.get(sTestName)).get("Auth_Username"),
//									(String) ((Hashtable) h2APIPrefs
//											.get(sTestName))
//											.get("Auth_Password"))
//							.contentType(ContentType.TEXT).headers(headers);
//				}
//			} else if (((String) ((Hashtable) h2APIPrefs.get(sTestName))
//					.get("Auth")).equalsIgnoreCase("NA"))
//				Resp = RestAssured.given().contentType(ContentType.JSON)
//						.headers(headers);
//			else {
//				Resp = RestAssured
//						.given()
//						.auth()
//						.preemptive()
//						.basic((String) ((Hashtable) h2APIPrefs.get(sTestName))
//								.get("Auth_Username"),
//								(String) ((Hashtable) h2APIPrefs.get(sTestName))
//										.get("Auth_Password"))
//						.contentType(ContentType.JSON).headers(headers);
//			}
//
//			hLastRequestObject.put(sTestName, Resp);
//
//			sDesc = sDesc + " Added headers successfully ::Header : "
//					+ headerName + ", and value : " + headerValue;
//
//			Reporter.print(sTestName, sDesc + "  :: Performed");
//		} catch (Exception e) {
//			sDesc = sDesc + " Adding headers FAILED";
//			sDesc = sDesc + " ERROR IN READING FILE";
//			if (Utils.handleIntermediateIssue())
//				raAddHeaders(sTestName, headerName, headerValue);
//			Reporter.printError(sTestName, e, sDesc);
//		}
//	}
//	private static class Helper{
//		static String read_File(String FilePath) throws IOException {
//			BufferedReader br = new BufferedReader(new FileReader(FilePath));
//		    try {
//		        StringBuilder sb = new StringBuilder();
//		        String line = br.readLine();
//
//		        while (line != null) {
//		            sb.append(line);
//		            sb.append("\n");
//		            line = br.readLine();
//		        }
//		        return sb.toString();
//		    } finally {
//		        br.close();
//		    }
//
//		}
//
//		static void check_Prefs(String sTestName) {
//			Hashtable<String,String> temp=new Hashtable<String,String>();
//			if (!h2APIPrefs.containsKey(sTestName)) {
//				temp.put("Auth", "NA");
//				temp.put("App_Type", "json");
//				h2APIPrefs.put(sTestName, temp);
//			}
//			else {
//				if (!h2APIPrefs.get(sTestName).containsKey("Auth"))
//					h2APIPrefs.get(sTestName).put("Auth", "NA");
//
//				if (!h2APIPrefs.get(sTestName).containsKey("App_Type"))
//					h2APIPrefs.get(sTestName).put("App_Type", "json");
//			}
//		}
//	}
//}
//*/