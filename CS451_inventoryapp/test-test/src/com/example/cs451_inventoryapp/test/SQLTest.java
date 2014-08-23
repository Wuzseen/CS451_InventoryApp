package com.example.cs451_inventoryapp.test;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.example.cs451_inventorypackage.*;

public class SQLTest extends TestCase {
	SQLHandler handler = new SQLHandler();

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        this.handler = new SQLHandler();
    }
    
    public void testConnected() {
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	String res = handler.makePost("http://www.timjbday.com/classtuff/hello.php", params);
    	String hell = "Hello\n";
    	Assert.assertEquals(hell, res);
    }
    
    public void testDBConnect() {
    	ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
    	String res = handler.makePost("http://www.timjbday.com/classtuff/TImConnect.php", params);
    	String control = "1\n";
    	Assert.assertEquals(control, res);
    }
}
