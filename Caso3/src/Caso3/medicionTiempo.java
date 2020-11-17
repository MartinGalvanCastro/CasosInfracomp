package Caso3;


public class medicionTiempo extends CrackerContrasenia{


	public medicionTiempo(int id){
		super(id);
	}



	@Override
	public void run() {
		double t = 0;
		double deltaT=0.001;
		synchronized (this) {
			while(!this.getListo()) {
				try {
					t+=deltaT;
					wait(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
		System.out.println("Se encontro la cadena en:" + t + " Segundos");
	}

}
