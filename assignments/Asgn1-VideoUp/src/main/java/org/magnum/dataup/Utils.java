package org.magnum.dataup;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.ReflectionException;

import org.magnum.dataup.model.Video;


public class Utils {
	
	public static String listToString(Collection c){
		return listToString(c, ", ");
	}
	
	public static String listToString(Collection c, String seperator){
		Iterator i = c.iterator();
		StringBuilder ret = new StringBuilder();
		while(i.hasNext())
			ret.append(i.next()+seperator);
		return ret.toString();
	}
	
	public static String listToStringVideo(Collection<Video> c, String seperator){
		if(c==null)
			return null;
		Iterator<Video> i = c.iterator();
		StringBuilder ret = new StringBuilder();
		while(i.hasNext())
			ret.append(toString(i.next())+seperator);
		return ret.toString();
	}
	
	public static String toString(Video v){
		return v.getTitle()+"("+v.getDuration()+") ";
	}
	
	/**
	 * Get Running url/ port
	 * @return
	 * @throws MalformedObjectNameException
	 * @throws NullPointerException
	 * @throws UnknownHostException
	 * @throws AttributeNotFoundException
	 * @throws InstanceNotFoundException
	 * @throws MBeanException
	 * @throws ReflectionException
	 */
	public static List<String> getUrls() throws MalformedObjectNameException,
		    NullPointerException, UnknownHostException, AttributeNotFoundException,
		    InstanceNotFoundException, MBeanException, ReflectionException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
		        Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
		String hostname = InetAddress.getLocalHost().getHostName();
		InetAddress[] addresses = InetAddress.getAllByName(hostname);
		ArrayList<String> endPoints = new ArrayList<String>();
		for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
		    ObjectName obj = i.next();
		    String scheme = mbs.getAttribute(obj, "scheme").toString();
		    String port = obj.getKeyProperty("port");
		    for (InetAddress addr : addresses) {
		        String host = addr.getHostAddress();
		        String ep = scheme + "://" + host + ":" + port;
		        endPoints.add(ep);
		    }
		}
		return endPoints;
	}
	
	public static int getPort(){
		String url;
		try {
			url = getUrls().get(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return Integer.parseInt(url.substring(url.indexOf(":")+1));
	}

}
