#version 430 core

in vec4 fragmentColour;
in vec2 pass_textures;

out vec4 out_Colour;

uniform sampler2D sampler;

void main(void) {
    out_Colour = texture(sampler, pass_textures);
    //out_Colour = fragmentColour;
    //out_Colour = vec4(red, green, blue, 0.0f);
}