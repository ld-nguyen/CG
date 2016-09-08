varying vec4 color;
varying vec2 uv;

vec4 prozFarbe(vec2 tex){
    float x = tex.x;
    float y = tex.y;
	vec4 neuFarbe = color *  vec4(x,y,0.7,1);
	return neuFarbe; 
}

void main (void) {
vec4 farbe = prozFarbe(uv);
gl_FragColor = farbe;
}
