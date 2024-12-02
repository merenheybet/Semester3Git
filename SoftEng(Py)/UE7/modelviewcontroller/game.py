import pygame
import time
import utils
import model
import controller
import view

# Possible game states
## GAME
class GameState:
    MENU = "menu"
    PLAYING_LEVEL1 = "playing_level1"
    PLAYING_LEVEL2 = "playing_level2"
    MULTIPLAYER = "multiplayer"
    GAME_OVER = "game_over"

class Level2State:
    LEVEL_V = 1
    LEVEL_H = 2

## GAME/UTILS
def init_game_level1():
     # Initialize player and traffic
    player1 = model.Player()
    traffic1 = model.Car()
    traffic2 = model.Truck()

    # Create sprite groups
    all_traffic = pygame.sprite.Group()
    all_traffic.add(traffic1)
    all_traffic.add(traffic2)

    all_sprites = pygame.sprite.Group()
    all_sprites.add(player1)
    all_sprites.add(traffic1)
    all_sprites.add(traffic2)

    return player1, all_traffic, all_sprites


def init_game_level2():
    # Initialize player and traffic
    player1 = model.PlayerXY()
    traffic1y = model.Car()
    traffic2y = model.Truck()
    traffic1x = model.CarX()
    traffic2x = model.TruckX()

    # Create sprite groups
    all_trafficY = pygame.sprite.Group()
    all_trafficY.add(traffic1y)
    all_trafficY.add(traffic2y)

    all_trafficX = pygame.sprite.Group()
    all_trafficX.add(traffic1x)
    all_trafficX.add(traffic2x)

    all_spritesY = pygame.sprite.Group()
    all_spritesY.add(player1)
    all_spritesY.add(traffic1y)
    all_spritesY.add(traffic2y)

    all_spritesX = pygame.sprite.Group()
    all_spritesX.add(player1)
    all_spritesX.add(traffic1x)
    all_spritesX.add(traffic2x)

    return player1, all_trafficY, all_trafficX, all_spritesY, all_spritesX


def init_game_multiplayer():
    # Initialize player and traffic
    player1 = model.PlayerXY()
    player2 = model.PlayerKeys()
    traffic_car1 = model.CarColored(utils.RED)
    traffic_truck1 = model.TruckColored(utils.RED)
    traffic_car2 = model.CarColored(utils.ORANGE)
    traffic_truck2 = model.TruckColored(utils.ORANGE)

    # Create sprite groups
    traffic1 = pygame.sprite.Group()
    traffic1.add(traffic_car1)
    traffic1.add(traffic_truck1)

    traffic2 = pygame.sprite.Group()
    traffic2.add(traffic_car2)
    traffic2.add(traffic_truck2)

    all_sprites = pygame.sprite.Group()
    all_sprites.add(player1)
    all_sprites.add(player2)
    all_sprites.add(traffic_car1)
    all_sprites.add(traffic_truck1)
    all_sprites.add(traffic_car2)
    all_sprites.add(traffic_truck2)

    return player1, player2, traffic1, traffic2, all_sprites


# Run the game
## GAMEE
def run():
    # Initialize pygame
    pygame.init()

    # Set display
    win = pygame.display.set_mode((utils.SCREEN_WIDTH, utils.SCREEN_HEIGHT))

    # Set clock
    clock = pygame.time.Clock()
    start_time = None

    FONT_LEVEL = pygame.font.SysFont("Sans", 20)

    TIME_LEVEL = 3000

    # Initialize Game state
    game_state = GameState.MENU
    level_state = 0
    count = 0

    # Run the game
    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False

        # Handle game state transitions
        if game_state == GameState.MENU:
            # display menu
            view.game_menu(win)
            # handle key input
            keys = pygame.key.get_pressed()

            if keys[pygame.K_1]:
                game_state = GameState.PLAYING_LEVEL1
                # init game
                player1, all_traffic, all_sprites = init_game_level1()
            if keys[pygame.K_2]:
                game_state = GameState.PLAYING_LEVEL2
                # init game
                player1, all_trafficY, all_trafficX, all_spritesY, all_spritesX = init_game_level2()
            if keys[pygame.K_m]:
                game_state = GameState.MULTIPLAYER
                view.multiplayer_screen(win)
                time.sleep(5)
                # init game
                player1, player2, traffic1, traffic2, all_sprites = init_game_multiplayer()
            if keys[pygame.K_ESCAPE]:
                running = False

        elif game_state == GameState.PLAYING_LEVEL1:
            # Move all objects
            for sprite in all_sprites:
                controller.move(sprite, sprite.axis, sprite.isNPC)

            # If player and traffic collide, display game over screen
            collision = pygame.sprite.spritecollideany(player1, all_traffic)
            if collision:
                # collision --> game over
                game_state = GameState.GAME_OVER

            else:
                # no collision --> draw all objects
                win.fill((0, 0, 0))
                all_sprites.draw(win)

        elif game_state == GameState.PLAYING_LEVEL2:

            if Level2State.LEVEL_V == (level_state % 3):
                # Move all objects
                for sprite in all_spritesY:
                    controller.move(sprite, sprite.axis, sprite.isNPC)

                # If player and traffic collide, display game over screen
                collision = pygame.sprite.spritecollideany(player1, all_trafficY)
                if collision:
                    # collision --> game over
                    game_state = GameState.GAME_OVER

                else:
                    # no collision --> draw all objects
                    win.fill((0, 0, 0))
                    all_spritesY.draw(win)

                if start_time:
                    time_since_switch = pygame.time.get_ticks() - start_time
                    message = 'Round: {} Time: {} ms'.format(count, (time_since_switch))
                    win.blit(FONT_LEVEL.render(message, True, utils.WHITE), (20, 20))
                    if time_since_switch > TIME_LEVEL:
                        count += 1
                        level_state += 1
                        start_time = pygame.time.get_ticks()

            elif Level2State.LEVEL_H == (level_state % 3):
                # Move all objects
                for sprite in all_spritesX:
                    controller.move(sprite, sprite.axis, sprite.isNPC)

                # If player and traffic collide, display game over screen
                collision = pygame.sprite.spritecollideany(player1, all_trafficX)
                if collision:
                    # collision --> game over
                    game_state = GameState.GAME_OVER

                else:
                    # no collision --> draw all objects
                    win.fill((0, 0, 0))
                    all_spritesX.draw(win)

                if start_time:
                    time_since_switch = pygame.time.get_ticks() - start_time
                    message = 'Round: {} Time: {} ms'.format(count, (time_since_switch))
                    win.blit(FONT_LEVEL.render(message, True, utils.WHITE), (20, 20))
                    if time_since_switch > TIME_LEVEL:
                        count += 1
                        level_state += 1
                        start_time = pygame.time.get_ticks()

            else:
                level_state += 1
                start_time = pygame.time.get_ticks()

        elif game_state == GameState.MULTIPLAYER:
            # Move all objects
            for sprite in all_sprites:
                controller.move(sprite, sprite.axis, sprite.isNPC)

            # If player and traffic collide, display game over screen
            collision1 = pygame.sprite.spritecollideany(player1, traffic1)
            collision2 = pygame.sprite.spritecollideany(player2, traffic2)
            if collision1 or collision2:
                # collision --> game over
                game_state = GameState.GAME_OVER

            else:
                # no collision --> draw all objects
                win.fill((0, 0, 0))
                all_sprites.draw(win)

        elif game_state == GameState.GAME_OVER:
            # display game over screen
            view.game_over(win)
            time.sleep(2)
            game_state = GameState.MENU
            start_time = None
            level_state = 0
            count = 0

        pygame.display.flip()
        clock.tick(30)

    pygame.quit()

## GAME
if __name__ == '__main__':
    run()