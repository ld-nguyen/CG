varying vec2 uv;
varying vec3 normal;
varying vec3 vertex;
uniform sampler2D s;
float ambient;
float diffuse;
float specular;

void main (void) {

vec3 V = -vertex;
V = normalize(V);

vec3 lichtPos = vec3(15,15,15)-vertex;
lichtPos = normalize(lichtPos);

ambient = 0.1;
diffuse = dot(normal,lichtPos);
vec3 r = reflect(lichtPos,normal);
specular = pow(max(0.0,dot(r,V)),3.0);

float phong = diffuse+ambient+specular;

gl_FragColor = texture2D(s,uv)*phong;

}