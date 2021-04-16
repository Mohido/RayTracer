# A Ray-Tracer prototype:
There are 2 techniques to render 3d objects to the screen: 
1) raytracing
2) rasterization
<br/>
Here, I apply the basics of the implementation of the raytracer technique.
<br/>

# This is a mid resolution Picture of a Sphere and a light reflection.<br/>
![Reflection mid resolution](https://user-images.githubusercontent.com/48254077/110088795-98145300-7d95-11eb-85c8-b73841334340.png)

<br/>
<br/>

# This is a low resolution "M" Letter printed on screen with spheres.<br/>
![M low resolution](https://user-images.githubusercontent.com/48254077/110088791-977bbc80-7d95-11eb-97a7-14bb4afeeb91.png)


# Notes:
1) No Bias is added to the doubles, thus leads to some incorrect calculations when the objects become a bit further in the scene from the camera.
2) Aliasing is not added which makes the objects edges looks rigid (as if you are using win32 GDI rendering ;\).

# Further-Dev-Notes:
1) Adding refractions.
2) Adding textures and world view.
3) Adding Triangles.
4) Pollishing and reduce the Delta Error (Errors from heavy double calculations).
