import pygame
import random
import utils

# needs move
class Player(pygame.sprite.Sprite):
    def __init__(self):
        # Define initial parameters
        self.color = utils.BLUE
        self.width = 50
        self.height = 100
        self.vel = 50
        self.axis = "x"
        self.isNPC = False

        # Call the parent class (Sprite) constructor
        pygame.sprite.Sprite.__init__(self)

        # Create a solid color block
        self.image = pygame.Surface([self.width, self.height])
        self.image.fill(self.color)

        # Fetch the color block
        self.rect = self.image.get_rect()

        # Set the initial position
        self.rect.x = utils.SCREEN_WIDTH / 2
        self.rect.y = utils.SCREEN_HEIGHT - self.height

# needs move
class PlayerXY(pygame.sprite.Sprite):
    def __init__(self):
        # Define initial parameters
        self.color = utils.BLUE
        self.width = 50
        self.height = 50
        self.vel = 50
        self.axis = "xy"
        self.isNPC = False

        # Call the parent class (Sprite) constructor
        pygame.sprite.Sprite.__init__(self)

        # Create a solid color block
        self.image = pygame.Surface([self.width, self.height])
        self.image.fill(self.color)

        # Fetch the color block
        self.rect = self.image.get_rect()

        # Set the initial position
        self.rect.x = utils.SCREEN_WIDTH / 2
        self.rect.y = utils.SCREEN_HEIGHT - self.height

# needs move Function
class PlayerKeys(pygame.sprite.Sprite):
    def __init__(self):
        # Define initial parameters
        self.color = utils.GREEN
        self.width = 50
        self.height = 50
        self.vel = 50
        self.axis = "xy2"
        self.isNPC = False

        # Call the parent class (Sprite) constructor
        pygame.sprite.Sprite.__init__(self)

        # Create a solid color block
        self.image = pygame.Surface([self.width, self.height])
        self.image.fill(self.color)

        # Fetch the color block
        self.rect = self.image.get_rect()

        # Set the initial position
        self.rect.x = utils.SCREEN_WIDTH / 2
        self.rect.y = utils.SCREEN_HEIGHT - self.height


# needs move Function
class Traffic(pygame.sprite.Sprite):
    def __init__(self, color, width, height, vel, increment):
        # Define initial parameters
        self.color = color
        self.width = width
        self.height = height
        self.vel = vel
        self.vel_increment = increment
        self.axis = "y"
        self.isNPC = True

        # Call the parent class (Sprite) constructor
        pygame.sprite.Sprite.__init__(self)

        # Create a solid color block
        self.image = pygame.Surface([self.width, self.height])
        self.image.fill(self.color)

        # Fetch the color block
        self.rect = self.image.get_rect()

        # Set the initial position
        self.rect.x = random.randint(self.width // 2, utils.SCREEN_WIDTH - self.width // 2)
        self.rect.y = self.height

# needs move Function
class TrafficX(pygame.sprite.Sprite):
    def __init__(self, color, width, height, vel, increment):
        # Define initial parameters
        self.color = color
        self.width = width
        self.height = height
        self.vel = vel
        self.vel_increment = increment
        self.axis = "x"
        self.isNPC = True

        # Call the parent class (Sprite) constructor
        pygame.sprite.Sprite.__init__(self)

        # Create a solid color block
        self.image = pygame.Surface([self.width, self.height])
        self.image.fill(self.color)

        # Fetch the color block
        self.rect = self.image.get_rect()

        # Set the initial position
        self.rect.x = utils.SCREEN_WIDTH
        self.rect.y = random.randint(self.height // 2, utils.SCREEN_HEIGHT - self.height // 2)

class Car(Traffic):
    def __init__(self):
        # Define initial parameters
        self.color = utils.RED
        self.width = 60
        self.height = 100
        self.vel = 20
        self.vel_increment = 2
        super(Car, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)

class Truck(Traffic):
    def __init__(self):
        # Define initial parameters
        self.color = utils.ORANGE
        self.width = 100
        self.height = 150
        self.vel = 10
        self.vel_increment = 5
        super(Truck, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)

# TrafficX subtype car
## MODEL
class CarX(TrafficX):
    def __init__(self):
        # Define initial parameters
        self.color = utils.RED
        self.width = 100
        self.height = 60
        self.vel = 20
        self.vel_increment = 2
        super(CarX, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)

# TrafficX subtype truck
## MODEL
class TruckX(TrafficX):
    def __init__(self):
        # Define initial parameters
        self.color = utils.ORANGE
        self.width = 150
        self.height = 110
        self.vel = 10
        self.vel_increment = 5
        super(TruckX, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)


# Traffic subtype colored truck
## MODEL
class TruckColored(Traffic):
    def __init__(self, color):
        # Define initial parameters
        self.color = color
        self.width = 150
        self.height = 110
        self.vel = 10
        self.vel_increment = 5
        super(TruckColored, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)

# Traffic subtype colored car
## MODEL
class CarColored(Traffic):
    def __init__(self, color):
        # Define initial parameters
        self.color = color
        self.width = 100
        self.height = 60
        self.vel = 20
        self.vel_increment = 2
        super(CarColored, self).__init__(self.color, self.width, self.height, self.vel, self.vel_increment)