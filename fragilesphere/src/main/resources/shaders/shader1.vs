#version 430 core

layout (location = 0) in vec3 position;
layout (location = 2) in vec3 vertexColour;
layout (location = 1) in vec2 textures;

out vec2 pass_textures;
out vec4 fragmentColour;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    //gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    
    pass_textures = textures;
    fragmentColour = vec4(vertexColour, 1.0f);
}