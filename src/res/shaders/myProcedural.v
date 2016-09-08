varying vec2 uv;
varying vec4 color;
uniform mat4 mvpMatrix;

void main(void) {
uv = gl_MultiTexCoord0.xy;
color = gl_Color;
gl_Position = mvpMatrix * gl_Vertex;
}