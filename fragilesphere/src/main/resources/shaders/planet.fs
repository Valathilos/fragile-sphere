#version 430 core

//  in vec3 fragmentColour;
//  //in vec2 pass_textures;

//  out vec3 out_Colour;

//  //uniform sampler2D sampler;

//  void main(void) {
//      //out_Colour = texture(sampler, pass_textures);
//      out_Colour = fragmentColour;
//  }

in vec2 pass_textureCoords;

out vec4 out_Colour;

uniform sampler2D textureSampler;

void main(void) {

  out_Colour = texture(textureSampler, pass_textureCoords);

}
