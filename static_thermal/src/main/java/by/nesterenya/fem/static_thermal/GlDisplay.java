package by.nesterenya.fem.static_thermal;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

import by.nesterenya.fem.primitives.Box;
  
public class GlDisplay extends GLCanvas implements GLEventListener {

	public enum DisplayType {
	    MODEL, MESH, RESULT, MESHRESULT
	  };
	
	private Box model;
	private GLU glu;
	private GL2 gl;
	private DisplayType displayType = DisplayType.MODEL;
	private Position position = new Position();
	
	public Position getPosition() {
		return position;
	}
	
	public GlDisplay() {
		 this.addGLEventListener(this);
		 
		 //Set start position of scene 
		 position.setAngle_x(-20);
		 position.setAngle_y(20);
	}
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void display(GLAutoDrawable drawable) {
		//if (analysis == null) return;
	    //if (displayType == null) return;
	    gl = drawable.getGL().getGL2();

	    // clear
	    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

	    // Сброс параметов модели отображения
	    gl.glLoadIdentity();
	    gl.glTranslatef(0.0f, 0.0f, -30.0f);

	    // фон
	    gl.glBegin(GL2GL3.GL_QUADS);
	    // white color
	    gl.glColor3f(0.42f, 0.55f, 0.83f);
	    gl.glVertex2f(30.0f, 20.0f);
	    gl.glVertex2f(-30.0f, 20.0f);
	    // blue color
	    gl.glColor3f(1.0f, 1.0f, 1.0f);
	    gl.glVertex2f(-30.0f, -20.0f);
	    gl.glVertex2f(30.0f, -20.0f);
	    gl.glEnd();

	    
	    // Сместить в нуть на 30
	    gl.glTranslatef(0.0f, 0.0f, 30.0f);

	    try {
	      switch (displayType) {
	        case MODEL:
	           if(model!=null) {  plotModel(); }
	          break;
	        case MESH:
	          //plotMesh();
	          break;
	        case RESULT:
	          //plotThermalResult();
	          break;
	        case MESHRESULT:
	          //plotMehResult();
	          break;
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		  // Получить GL контекст
	    GL2 gl = drawable.getGL().getGL2();
	    // Получить GL инструменты
	    glu = new GLU();
	    // Цвет фона
	    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    // Установить очистку буфера глубины
	    gl.glClearDepth(1.0f);
	    gl.glEnable(GL_DEPTH_TEST);
	    gl.glDepthFunc(GL_LEQUAL);
	    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // лучшая
	                                                          // настройка
	                                                          // перспективы
	    gl.glShadeModel(GL_SMOOTH); // вклиютить смешение цветов, размытие
	                                // и освещение
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int e, int width,
			int height) {
		 // Получить контекст OpenGL 2
	    GL2 gl = drawable.getGL().getGL2();
	    // Проверка деления на ноль
	    if (height == 0) height = 1;
	    float aspect = (float) width / height;
	    // Установить окна отбражения
	    gl.glViewport(0, 0, width, height);

	    // Установить перспективную проекцию
	    // Выбор матрицы проекций
	    gl.glMatrixMode(GL_PROJECTION);

	    // Сбросить матрицу проекций
	    gl.glLoadIdentity();

	    // fovy, aspect, zNear, zFar
	    glu.gluPerspective(45.0, aspect, 0.1, 100.0);
	    // Включить model-view перемещения
	    gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
	    // Сбросить матрицу проекций
	    gl.glLoadIdentity();
	}

	public Box getModel() {
		return model;
	}

	public void setModel(Box model) {
		this.model = model;
	}
	
	
	private void plotModel() {

	    gl.glTranslatef(0.0f, 0.0f, -6.0f);
	    gl.glScaled(position.getZoom(), position.getZoom(), position.getZoom());

	    // TODO изменить положение камеры правильным образом
	    // gluLookAt(0.0, 0.0, 25.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

	    gl.glRotated(position.getAngle_x(), 0.0, 1.0, 0.0);
	    gl.glRotated(position.getAngle_y(), 1.0, 0.0, 0.0);

	    // Рисуем координатные оси
	    // TODO перейменовать
	    GLPrimitives.drawCoordinateSystem(gl);

	    gl.glTranslated(position.getMove_x(), position.getMove_y(), 0);

	    gl.glPushMatrix();

	    // Материал серебро
	    float ambient[] = {0.0215f, 0.1745f, 0.0215f, 1.0f};
	    float diffuse[] = {0.07568f, 0.61424f, 0.07568f, 1.0f};
	    float specular[] = {0.508273f, 0.508273f, 0.508273f, 1.0f};
	    float shine = 0.4f;

	    gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_AMBIENT, FloatBuffer.wrap(ambient));
	    gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_DIFFUSE, FloatBuffer.wrap(diffuse));
	    gl.glMaterialfv(GL.GL_FRONT, GLLightingFunc.GL_SPECULAR, FloatBuffer.wrap(specular));
	    gl.glMaterialf(GL.GL_FRONT, GLLightingFunc.GL_SHININESS, shine * 128.0f);

	    gl.glColor3f(0.83f, 0.83f, 0.83f);

	    // TODO Можно добавить обстрактную фабрику для создания метода геометрии
	    GLPrimitives.drawBox(gl, model);

	    gl.glColor3f(0.3f, 0.3f, 0.3f);
	    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
	    
	    GLPrimitives.drawBox(gl, model);
	    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);

	    gl.glPopMatrix();

	    // Ждать завершения прорисовки
	    gl.glFlush();
	  }
	
}
