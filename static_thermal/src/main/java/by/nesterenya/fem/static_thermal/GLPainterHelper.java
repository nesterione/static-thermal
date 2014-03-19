package by.nesterenya.fem.static_thermal;

import java.nio.FloatBuffer;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.fixedfunc.GLLightingFunc;

import by.nesterenya.fem.mesh.IMesh;
import by.nesterenya.fem.element.*;
import by.nesterenya.fem.element.INode.Dim;
import by.nesterenya.fem.primitives.Box;

public class GLPainterHelper {
	
	  // TODO Может быть переместить в лучшее место, может методы расширения
	  private static void drawGlVertex3d(GL2 gl,INode node) throws Exception {
	    gl.glVertex3d(node.getValueOfDemention(Dim.X), node.getValueOfDemention(Dim.Y),
	        node.getValueOfDemention(Dim.Z));
	  }

	
	public static void plotMesh(GL2 gl, Position position, IMesh mesh) throws Exception {
	    gl.glTranslatef(0.0f, 0.0f, -6.0f);
	    gl.glScaled(position.getZoom(), position.getZoom(), position.getZoom());

	    // TODO изменить положение камеры правильным образом
	    // gluLookAt(0.0, 0.0, 25.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

	    gl.glRotated(position.getAngle_x(), 0.0, 1.0, 0.0);
	    gl.glRotated(position.getAngle_y(), 1.0, 0.0, 0.0);

	    // Рисуем координатные оси
	    GLPrimitives.drawCoordinateSystem(gl);

	    gl.glTranslated(position.getMove_x(),position.getMove_y(), 0);

	    gl.glEnable(GL2ES1.GL_ALPHA_TEST);
	    gl.glEnable(GL.GL_BLEND);
	    gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

	    gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

	    gl.glEnable(GL2ES1.GL_POINT_SMOOTH); // включаем режим сглаживания точек
	    gl.glPointSize(4);
	    gl.glBegin(GL.GL_POINTS);


	    for (INode node : mesh.getNodes()) {
	      drawGlVertex3d(gl,node);
	    }

	    gl.glEnd();

	    // Включить отрисовку линий, цвет линий синий
	    gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
	    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_LINE);
	    // Смещение, для того чтобы лиини были лучше видны
	    gl.glTranslatef(0, 0, 0.001f);

	    // Отрисовываем все конечные элементы
	    gl.glBegin(GL.GL_TRIANGLES);
	    List<IElement> elements = mesh.getElements();
	    for (IElement element : elements) {
	      INode node0 = element.getNode(0);
	      INode node1 = element.getNode(1);
	      INode node2 = element.getNode(2);
	      INode node3 = element.getNode(3);

	      drawGlVertex3d(gl,node0);
	      drawGlVertex3d(gl,node1);
	      drawGlVertex3d(gl,node2);

	      drawGlVertex3d(gl,node0);
	      drawGlVertex3d(gl,node1);
	      drawGlVertex3d(gl,node3);

	      drawGlVertex3d(gl,node1);
	      drawGlVertex3d(gl,node2);
	      drawGlVertex3d(gl,node3);

	      drawGlVertex3d(gl,node0);
	      drawGlVertex3d(gl,node2);
	      drawGlVertex3d(gl,node3);
	    }
	    gl.glEnd();

	    // Выключить отображение полигонов в виде линий
	    gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2GL3.GL_FILL);
	    // Сместить в исходное положение
	    //gl.glTranslatef(0, 0, -0.001f);

	    //gl.glColor4d(0.85, 0.85, 0.85, 0.45f);
	    
	    //GLPrimitives.drawBox(gl, analysis.getGeometry());

	    //drawLoads();
	    //drawSelectedPlate();

	    gl.glDisable(GL.GL_BLEND);
	    gl.glDisable(GL2ES1.GL_ALPHA_TEST);

	    gl.glFlush();
	}
	
	public static void plotModel(GL2 gl, Position position, Box model) {

	    gl.glTranslatef(0.0f, 0.0f, -6.0f);
	    gl.glScaled(position.getZoom(), position.getZoom(), position.getZoom());

	    // TODO изменить положение камеры правильным образом
	    // gluLookAt(0.0, 0.0, 25.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

	    gl.glRotated(position.getAngle_x(), 0.0, 1.0, 0.0);
	    gl.glRotated(position.getAngle_y(), 1.0, 0.0, 0.0);

	    // Рисуем координатные оси
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
