#version 430 core

in vec3 fragmentColour;
//in vec2 pass_textures;

out vec3 out_Colour;

//uniform sampler2D sampler;

void main(void) {
    //out_Colour = texture(sampler, pass_textures);
    out_Colour = fragmentColour;
}