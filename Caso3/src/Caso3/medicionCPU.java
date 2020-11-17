package Caso3;

import java.lang.management.ManagementFactory;
import java.security.NoSuchAlgorithmException;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class medicionCPU extends CrackerContrasenia{


	public medicionCPU(int id){
		super(id);
	}

	public double getSystemCpuLoad() throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		javax.management.AttributeList list = mbs.getAttributes(name, new String[]{ "SystemCpuLoad" });

		if (list.isEmpty()) return Double.NaN;

		Attribute att = (Attribute)list.get(0);
		Double value = (Double)att.getValue();

		// usually takes a couple of seconds before we get real values
		if (value == -1.0) return Double.NaN;
		// returns a percentage value with 1 decimal point precision
		return ((int)(value * 1000) / 10.0);
	}


	@Override
	public void run() {
		synchronized (this) {
			while(!this.getListo()) {
				try {
					System.out.println("La carga actual del CPU es: " + getSystemCpuLoad());
					wait(1000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}

	}

}
