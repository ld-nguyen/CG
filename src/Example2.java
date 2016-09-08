import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import lenz.opengl.AbstractSimpleBase;
import lenz.opengl.utils.ShaderProgram;
import lenz.opengl.utils.Texture;

public class Example2 extends AbstractSimpleBase {
	int delta = 0;
	int deltaTimeGlobal = 0;
	long frame = Sys.getTime();
	Texture wall;
	ShaderProgram shade;
	ShaderProgram proz;
	Matrix4f projection;

	public static void main(String[] args) {

		new Example2().start();
	}

	@Override
	protected void initOpenGL() {
		//glMatrixMode(GL_PROJECTION);
		//glFrustum(-10, 10, -10, 10, 1, 100);
		glMatrixMode(GL_MODELVIEW);
		glShadeModel(GL_FLAT);
		glEnable(GL_CULL_FACE);
		wall = new Texture("UV.png");
	shade = new ShaderProgram("myPhongTest");
	proz = new ShaderProgram("myProcedural");
	projection = new Matrix4f();
	projection.m20 = 0f;
	projection.m21 = 0f;
	projection.m22 = -1*101/99f;
	projection.m23 = -1f;
	projection.m32 = -2*100/99f;
	projection.m33 = 0;
	}

	@Override
	protected void render() {
		
		
		long t = (int) Sys.getTime();
		long d = t - frame;
		frame = t;
		double deltaTime = (60*d)/1000;

		deltaTimeGlobal += deltaTime;

		glClear(GL_COLOR_BUFFER_BIT);
		//glLoadIdentity();
		glEnable(GL_TEXTURE_2D);
		//glBindTexture(GL_TEXTURE_2D,wall.getId());

		
		FloatBuffer mvpBuffer = BufferUtils.createFloatBuffer(16);
	
		GL20.glUseProgram(shade.getId());
		Matrix4f mvp = new Matrix4f();
		mvp.translate(new Vector3f(2,0,-5));
//		mvp.scale(new Vector3f(1,1,1));
		mvp.rotate(deltaTimeGlobal/80f, new Vector3f(1,0,0));
		mvp.store(mvpBuffer);
		mvpBuffer.flip();
		glUniformMatrix4(glGetUniformLocation(shade.getId(), "mvpMatrix"),
				false, mvpBuffer);
		
		projection.store(mvpBuffer);
		mvpBuffer.flip();
		glUniformMatrix4(glGetUniformLocation(shade.getId(), "projMatrix"),
				false, mvpBuffer);
		
		Matrix4f normalMatrix = new Matrix4f(); 
		Matrix4f.invert(mvp, normalMatrix);
		
		normalMatrix.store(mvpBuffer);
		mvpBuffer.flip();
		glUniformMatrix4(glGetUniformLocation(shade.getId(), "normMatrix"),
				true, mvpBuffer);
		
		
		glBegin(GL_QUADS);
		//vorn
		glColor3f(1,1,1);
		glNormal3f(0, 0, 1);
		glTexCoord2d(0, 0);		
		glVertex3f(-1,-1,1);	
		glTexCoord2d(1, 0);
		glVertex3f(1,-1,1);
		glTexCoord2d(1, 1);
		glVertex3f(1,1,1);
		glTexCoord2d(0, 1);
		glVertex3f(-1,1,1);


		//hinten
		glNormal3f(0, 0, -1);
		glTexCoord2d(0, 0);	
		glVertex3f(1,-1,-1);		
		glTexCoord2d(0.1, 0);	
		glVertex3f(-1,-1,-1);		
		glTexCoord2d(0.1, 0.1);	
		glVertex3f(-1,1,-1);		
		glTexCoord2d(0, 0.1);	
		glVertex3f(1,1,-1);


		//rechts
		glNormal3f(1, 0, 0);
		glTexCoord2d(0, 0);	
		glVertex3f(1,-1,1);
		glTexCoord2d(0.5, 0);	
		glVertex3f(1,-1,-1);
		glTexCoord2d(0.5, 0.5);	
		glVertex3f(1,1,-1);
		glTexCoord2d(0, 0.5);	
		glVertex3f(1,1,1);

		//links
		glNormal3f(-1, 0, 0);
		glTexCoord2d(0, 0);	
		glVertex3f(-1,-1,1);
		glTexCoord2d(0.2, 0);	
		glVertex3f(-1,1,1);
		glTexCoord2d(0.2, 0.2);	
		glVertex3f(-1,1,-1);
		glTexCoord2d(0, 0.2);	
		glVertex3f(-1,-1,-1);


		//oben
		glNormal3f(0, 1, 0);
		glTexCoord2d(0, 0);	
		glVertex3f(-1,1,1);
		glTexCoord2d(0.6, 0);	
		glVertex3f(1,1,1);
		glTexCoord2d(0.6, 0.6);	
		glVertex3f(1,1,-1);
		glTexCoord2d(0, 0.6);	
		glVertex3f(-1,1,-1);

		//unten
		glNormal3f(0, -1, 0);
		glTexCoord2d(0, 0);	
		glVertex3f(1,-1,1);
		glTexCoord2d(0.3, 0);	
		glVertex3f(-1,-1,1);
		glTexCoord2d(0.3, 0.3);	
		glVertex3f(-1,-1,-1);
		glTexCoord2d(0, 0.3);	
		glVertex3f(1,-1,-1);
		glEnd();
		
		//Körper 2
		
		GL20.glUseProgram(proz.getId());
		mvp = new Matrix4f(projection);
		mvp.translate(new Vector3f(-3,0,-8));
		mvp.scale(new Vector3f(2,2,2));
		mvp.rotate(deltaTimeGlobal/100f, new Vector3f(0,1,0));
		mvp.store(mvpBuffer);
		mvpBuffer.flip();
		glUniformMatrix4(glGetUniformLocation(proz.getId(), "mvpMatrix"),
				false, mvpBuffer);
		glBegin(GL_QUADS);
		//tetraeder
		
		//Standfläche unten
		glColor3f(1,1,1);
		glNormal3f(0,-1,0);
		glTexCoord2d(0, 0);
		glVertex3f(-1,-1,-1); //A
		glTexCoord2d(1, 1);
		glVertex3f(1,-1,-1); //D
		glTexCoord2d(1, 0);
		glVertex3f(1,-1,1); //C
		glTexCoord2d(0, 1);
		glVertex3f(-1,-1,1); //B
		
		glEnd();
		glBegin(GL_TRIANGLES);
		
		//Fläche vorne
		glColor3f(1f,1f,0f);
		glNormal3f(0,1,1);
		glTexCoord2d(0, 1);
		glVertex3f(0,1,0); //Spitze
		glTexCoord2d(1, 1);
		glVertex3f(-1,-1,1); //B
		glTexCoord2d(1, 0);
		glVertex3f(1,-1,1); //C
		
		//Fläche Links
		glColor3f(1f,0f,1f);
		glNormal3f(-1,1,0);
		glTexCoord2d(0, 0);
		glVertex3f(0,1,0); //Spitze
		glTexCoord2d(0, 1);
		glVertex3f(-1,-1,-1); //A
		glTexCoord2d(1, 1);
		glVertex3f(-1,-1,1);//B
		
		//Fläche rechts
		glColor3f(1f,0f,1f);
		glNormal3f(1,1,0);
		glTexCoord2d(0.5, 1);
		glVertex3f(0,1,0); //Spitze
		glTexCoord2d(1, 1);
		glVertex3f(1,-1,1); //C
		glTexCoord2d(1, 0);
		glVertex3f(1,-1,-1); //D
		
		//Fläche hinten
		glColor3f(0f,1f,1f);
		glNormal3f(0,1,-1);
		glTexCoord2d(0, 1);
		glVertex3f(0,1,0); //S
		glTexCoord2d(1, 1);
		glVertex3f(1,-1,-1); //D
		glTexCoord2d(1, 0);
		glVertex3f(-1,-1,-1); //A
		
		glEnd();
		
	}
}
