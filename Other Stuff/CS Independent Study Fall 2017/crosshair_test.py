import pygame, math, random

# Borrowed code/information from:
# www.pygame.org/docs/
# www.programarcadegames.com/index.php?chapter=introduction_to_graphics&lang=en#section_5

# Default Colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)

# Initialization of playing field
pygame.init()
size = (700, 500)
screen = pygame.display.set_mode(size)
pygame.display.set_caption("Crosshair Test")

# Outer Variables (not refreshed within the loop)
done = False
clock = pygame.time.Clock()
count = 0
radius = 20
shrink_grow = False

while not done:
    # Inner Variables (refreshed within the loop)
    pos = pygame.mouse.get_pos()
    if count % 5 == 0:
    	randcolor = (random.randint(70, 230), random.randint(70, 230), random.randint(70, 230))
    # Event Loop
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            done = True
        elif event.type == pygame.MOUSEBUTTONDOWN:
        	shrink_grow = True
        elif event.type == pygame.MOUSEBUTTONUP:
        	shrink_grow = False
    
    # Screen Refresh & Drawing
    count += 1
    if shrink_grow:
    	if radius >= 100:
    		mult = -1
    	if radius <= 20:
    		mult = 1
    	radius += 2*mult
    screen.fill(BLACK)
    pygame.draw.line(screen, randcolor, (pos[0], pos[1] - 10), (pos[0], pos[1] + 10), 2)
    pygame.draw.line(screen, randcolor, (pos[0] - 10, pos[1]), (pos[0] + 10, pos[1]), 2)
    pygame.draw.circle(screen, randcolor, tuple(map(lambda x: x + 1, pos)), radius, 3)
    pygame.display.flip()
    clock.tick(60)

pygame.quit()
