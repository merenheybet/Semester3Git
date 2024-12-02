import pygame
import utils

def game_over(win):
    win.fill((255, 0, 0))
    text_surface = utils.font.render("Game Over", True, utils.WHITE)
    win.blit(text_surface, (utils.SCREEN_WIDTH * 0.4, utils.SCREEN_HEIGHT * 0.5))
    pygame.display.flip()

# Display instructions for multiplayer mode
## VIEW
def multiplayer_screen(win):
    win.fill((0, 0, 0))
    player_1 = utils.font.render("Player1:", True, utils.WHITE)
    player_1_color = utils.font.render("Color - Blue", True, utils.WHITE)
    player_1_keys = utils.font.render("Keys - Arrow Keys", True, utils.WHITE)
    player_1_traffic = utils.font.render("Traffic - Red", True, utils.WHITE)

    player_2 = utils.font.render("Player2:", True, utils.WHITE)
    player_2_color = utils.font.render("Color - Green", True, utils.WHITE)
    player_2_keys = utils.font.render("Keys - WASD", True, utils.WHITE)
    player_2_traffic = utils.font.render("Traffic - Orange", True, utils.WHITE)

    win.blit(player_1, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.1))
    win.blit(player_1_color, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.2))
    win.blit(player_1_keys, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.3))
    win.blit(player_1_traffic, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.4))

    win.blit(player_2, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.6))
    win.blit(player_2_color, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.7))
    win.blit(player_2_keys, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.8))
    win.blit(player_2_traffic, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.9))
    pygame.display.flip()


# Display menu with options to start or quit the game
## VIEW
def game_menu(win):
    win.fill((0, 0, 0))
    text_surface = utils.font.render("Game Menu", True, utils.WHITE)
    win.blit(text_surface, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.3))

    start_surface = utils.font.render("Press 1 to start level 1", True, utils.WHITE)
    win.blit(start_surface, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.4))

    start_surface = utils.font.render("Press 2 to start level 2", True, utils.WHITE)
    win.blit(start_surface, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.5))

    multiplayer_surface = utils.font.render("Press M for mutliplayer", True, utils.WHITE)
    win.blit(multiplayer_surface, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.6))

    quit_surface = utils.font.render("Press ESC to quit", True, utils.WHITE)
    win.blit(quit_surface, (utils.SCREEN_WIDTH * 0.2, utils.SCREEN_HEIGHT * 0.7))
    pygame.display.flip()