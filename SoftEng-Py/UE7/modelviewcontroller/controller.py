import pygame
import random
import utils

#### Auch die Spiellogik muss hier sein

def move(model, axis, isNPC):
    if isNPC:
        match axis:
            case "x":
                model.rect.x -= model.vel  # move to left
                # If traffic has moved to the left reset it to the right
                if model.rect.left < 0:
                    model.rect.y = random.randint(model.height // 2, utils.SCREEN_HEIGHT - model.height // 2)
                    model.rect.x = utils.SCREEN_WIDTH
                    # Speed up traffic
                    model.vel += model.vel_increment

            case "y":
                model.rect.y += model.vel
                # If traffic has moved to the bottom reset it to the top
                if model.rect.top >= utils.SCREEN_HEIGHT:
                    model.rect.y = 0
                    model.rect.x = random.randint(model.width // 2, utils.SCREEN_WIDTH - model.width // 2)
                    # Speed up traffic
                    model.vel += model.vel_increment

    elif not isNPC:
        match axis:
            case "x":
                keys = pygame.key.get_pressed()

                # Move to the left if it's not on the leftmost side
                if keys[pygame.K_LEFT]:
                    if model.rect.left > 0:
                        model.rect.x -= model.vel

                # Move to the right if it's not on the rightmost side
                elif keys[pygame.K_RIGHT]:
                    if model.rect.right < utils.SCREEN_WIDTH:
                        model.rect.x += model.vel

            case "xy":
                keys = pygame.key.get_pressed()

                # Move to the left if it's not on the leftmost side
                if keys[pygame.K_LEFT]:
                    if model.rect.left > 0:
                        model.rect.x -= model.vel

                # Move to the right if it's not on the rightmost side
                elif keys[pygame.K_RIGHT]:
                    if model.rect.right < utils.SCREEN_WIDTH:
                        model.rect.x += model.vel

                # Move to the top if it's not on the uppermost side
                elif keys[pygame.K_UP]:
                    if model.rect.top > 0:
                        model.rect.y -= model.vel

                # Move to the bottom if it's not on the bottommost side
                elif keys[pygame.K_DOWN]:
                    if model.rect.bottom < utils.SCREEN_HEIGHT:
                        model.rect.y += model.vel

            case "xy2":
                # Get keyboard input
                keys = pygame.key.get_pressed()

                # Move to the left if it's not on the leftmost side
                if keys[pygame.K_a]:
                    if model.rect.left > 0:
                        model.rect.x -= model.vel

                # Move to the right if it's not on the rightmost side
                elif keys[pygame.K_d]:
                    if model.rect.right < utils.SCREEN_WIDTH:
                        model.rect.x += model.vel

                # Move to the top if it's not on the uppermost side
                elif keys[pygame.K_w]:
                    if model.rect.top > 0:
                        model.rect.y -= model.vel

                # Move to the bottom if it's not on the bottommost side
                elif keys[pygame.K_s]:
                    if model.rect.bottom < utils.SCREEN_HEIGHT:
                        model.rect.y += model.vel
