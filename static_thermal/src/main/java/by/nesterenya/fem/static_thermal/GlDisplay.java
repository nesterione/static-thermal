package by.nesterenya.fem.static_thermal;

import static javax.media.opengl.GL.*;
import static javax.media.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;


public class GlDisplay extends GLCanvas implements GLEventListener {

	private GLU glu;
	private GL2 gl;
	
	public GlDisplay() {
		 this.addGLEventListener(this);
	}
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void display(GLAutoDrawable drawable) {
		//if (analysis == null) return;
	    //if (displayType == null) return;
		System.out.println("sd");
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
	    /*gl.glTranslatef(0.0f, 0.0f, 30.0f);

	    try {
	      switch (displayType) {
	        case MODEL:
	          plotModel();
	          break;
	        case MESH:
	          plotMesh();
	          break;
	        case RESULT:
	          plotThermalResult();
	          break;
	        case MESHRESULT:
	          plotMehResult();
	          break;
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }*/
		
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
}
