#version 150 core

uniform sampler2D texture_diffuse;
uniform mat4 stMatrix;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) {
	//out_Color = pass_Color;
	// Override out_Color with our texture pixel
	vec4 texCoord4 = vec4(0,0,0,1);
	texCoord4.xy = pass_TextureCoord;
	out_Color = texture(texture_diffuse, (stMatrix*texCoord4).xy);
}