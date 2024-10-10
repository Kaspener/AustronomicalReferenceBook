attribute vec4 a_Position;
attribute vec3 a_Normal;

uniform mat4 u_MVPMatrix;
uniform mat4 u_ModelMatrix;
uniform mat3 u_NormalMatrix;

varying vec3 v_Normal;
varying vec3 v_Position;

void main() {
    v_Position = vec3(u_ModelMatrix * a_Position);
    v_Normal = normalize(u_NormalMatrix * a_Normal);
    gl_Position = u_MVPMatrix * a_Position;
}