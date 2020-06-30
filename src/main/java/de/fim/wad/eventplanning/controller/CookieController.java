/**
 * package de.fim.wad.eventplanning.controller;
 *
 * import org.springframework.boot.web.servlet.server.Session;
 * import org.springframework.web.bind.annotation.CookieValue;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.RestController;
 *
 * import javax.servlet.http.Cookie;
 * import javax.servlet.http.HttpServletRequest;
 * import javax.servlet.http.HttpServletResponse;
 *
 * @RestController
 * public class CookieController {
 *
 *
 *     @GetMapping("/")
 *     public String setCookieLikes(HttpServletResponse response){
 *         Cookie cookie = new Cookie("likes", "");
 *         // 1 day
 *         cookie.setMaxAge(1 * 24 * 60 * 60);
 *         cookie.setSecure(true);
 *         cookie.setHttpOnly(true);
 *         // global cookie accessible every where
 *         cookie.setPath("/");
 *         //add cookie to response
 *         response.addCookie(cookie);
 *
 *         return "CookieLikes initiated";
 *     }
 *
 *
 *     @GetMapping("/")
 *     public String setCookieDislikes(HttpServletResponse response) {
 *         Cookie cookie = new Cookie("dislikes", "");
 *         // 1 day
 *         cookie.setMaxAge(1 * 24 * 60 * 60);
 *         cookie.setSecure(true);
 *         cookie.setHttpOnly(true);
 *         // global cookie accessible every where
 *         cookie.setPath("/");
 *         //add cookie to response
 *         response.addCookie(cookie);
 *
 *         return "CookieDislikes initiated";
 *     }
 *
 *     @GetMapping("/detail")
 *     public String readAllCookies(HttpServletRequest request) {
 *
 *     }
 *
 *     @GetMapping("/detail")
 *     public String setCookie(HttpServletResponse response) {
 *
 *     }
 * }
 */
