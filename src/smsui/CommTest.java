
package misc;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Formatter;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;

public class CommTest
{
	private static final String _NO_DEVICE_FOUND = "  no device found";

	private final static Formatter _formatter = new Formatter(System.out);

	static CommPortIdentifier portId;

	static Enumeration<CommPortIdentifier> portList;

	static int bauds[] = { 9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200 };

	/**
	 * Wrapper around {@link CommPortIdentifier#getPortIdentifiers()} to be
	 * avoid unchecked warnings.
	 */
	private static Enumeration<CommPortIdentifier> getCleanPortIdentifiers()
	{
		return CommPortIdentifier.getPortIdentifiers();
	}

	public static void main(String[] args)
	{
		System.out.println("\nSearching for devices...");
		portList = getCleanPortIdentifiers();
		while (portList.hasMoreElements())
		{
			portId = portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				_formatter.format("%nFound port: %-5s%n", portId.getName());
				for (int i = 0; i < bauds.length; i++)
				{
					SerialPort serialPort = null;
					_formatter.format("       Trying at %6d...", bauds[i]);
					try
					{
						InputStream inStream;
						OutputStream outStream;
						int c;
						String response;
						serialPort = portId.open("SMSLibCommTester", 1971);
						serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
						serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						inStream = serialPort.getInputStream();
						outStream = serialPort.getOutputStream();
						serialPort.enableReceiveTimeout(1000);
						c = inStream.read();
						while (c != -1)
							c = inStream.read();
						outStream.write('A');
						outStream.write('T');
						outStream.write('\r');
						Thread.sleep(1000);
						response = "";
						c = inStream.read();
						while (c != -1)
						{
							response += (char) c;
							c = inStream.read();
						}
						if (response.indexOf("OK") >= 0)
						{
							try
							{
								System.out.print("  Getting Info...");
								outStream.write('A');
								outStream.write('T');
								outStream.write('+');
								outStream.write('C');
								outStream.write('G');
								outStream.write('M');
								outStream.write('M');
								outStream.write('\r');
								response = "";
								c = inStream.read();
								while (c != -1)
								{
									response += (char) c;
									c = inStream.read();
								}
								System.out.println(" Found: " + response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", ""));
							}
							catch (Exception e)
							{
								System.out.println(_NO_DEVICE_FOUND);
							}
						}
						else
						{
							System.out.println(_NO_DEVICE_FOUND);
						}
					}
					catch (Exception e)
					{
						System.out.print(_NO_DEVICE_FOUND);
						Throwable cause = e;
						while (cause.getCause() != null)
						{
							cause = cause.getCause();
						}
						System.out.println(" (" + cause.getMessage() + ")");
					}
					finally
					{
						if (serialPort != null)
						{
							serialPort.close();
						}
					}
				}
			}
		}
		System.out.println("\nTest complete.");
	}
}
