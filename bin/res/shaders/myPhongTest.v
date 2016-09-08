varying vec2 uv;
varying vec3 normal;
varying vec3 vertex;
uniform mat4 mvpMatrix;
uniform mat4 projMatrix;
uniform mat4 normMatrix;

void main(void) {
uv=gl_MultiTexCoord0.xy;
normal = (normMatrix * vec4(gl_Normal,0)).xyz;
vertex = (mvpMatrix * gl_Vertex).xyz;
gl_Position = (projMatrix*mvpMatrix) * gl_Vertex;
}