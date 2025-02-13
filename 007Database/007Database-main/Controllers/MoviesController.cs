    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.EntityFrameworkCore;
    using MvcMovie.Data;
    using MvcMovie.Models;

    namespace JamesBondMovieDatabase.Controllers
    {
        //MoviesController extends Controller
        public class MoviesController : Controller
        {
            private readonly MvcMovieContext _context;

            public MoviesController(MvcMovieContext context)
            {
                _context = context;
            }

            //search bar implementation and title sorting on index page for movie
            public async Task<IActionResult> Index(string searchString, string sortOrder)
            {
                var movies = from m in _context.Movies
                             select m;

                if (!String.IsNullOrEmpty(searchString))
                {
                    movies = movies.Where(s => s.Title.Contains(searchString));
                }

                ViewBag.NameSortParm = String.IsNullOrEmpty(sortOrder) ? "name_desc" : "";
                ViewBag.DateSortParm = sortOrder == "Date" ? "date_desc" : "Date";
                movies = from m in _context.Movies
                               select m;
                switch (sortOrder)
                {
                    case "name_desc":
                        movies = movies.OrderBy(m => m.Title);
                        break;
                    case "Date":
                        movies = movies.OrderBy(m => m.Year);
                        break;
                    case "date_desc":
                        movies = movies.OrderByDescending(m => m.Year);
                        break;
                    default:
                        movies = movies.OrderBy(m => m.Director);
                        break;
                }

                return View(await movies.ToListAsync());
            }

            public async Task<IActionResult> Details(int? id)
            {
                if (id == null)
                {
                    return NotFound();
                }

                var movie = await _context.Movies
                    .FirstOrDefaultAsync(m => m.Id == id);
                if (movie == null)
                {
                    return NotFound();
                }

                return View(movie);
            }


            public IActionResult Create()
            {
                return View();
            }


            [HttpPost]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> Create([Bind("Id,Title,Year,Director,Synopsis")] Movie movie)
            {
                if (ModelState.IsValid)
                {
                    _context.Add(movie);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Index));
                }
                //select list with title replacing Id which improves UX and
                //maintains links Id to Movie
                ViewData["MovieId"] = new SelectList(_context.Movies, "Id", "Title", movie.Title);
                return View(movie);
            }


            public async Task<IActionResult> Edit(int? id)
            {
                if (id == null)
                {
                    return NotFound();
                }

                var movie = await _context.Movies.FindAsync(id);
                if (movie == null)
                {
                    return NotFound();
                }
                ViewData["MovieId"] = new SelectList(_context.Movies, "Id", "Title", movie.Title);
                return View(movie);
            }

            [HttpPost]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> Edit(int id, [Bind("Id,Title,Year,Director,Synopsis")] Movie movie)
            {
                if (id != movie.Id)
                {
                    return NotFound();
                }

                if (ModelState.IsValid)
                {
                    try
                    {
                        _context.Update(movie);
                        await _context.SaveChangesAsync();
                    }
                    catch (DbUpdateConcurrencyException)
                    {
                        if (!MovieExists(movie.Id))
                        {
                            return NotFound();
                        }
                        else
                        {
                            throw;
                        }
                    }
                    return RedirectToAction(nameof(Index));
                }
                ViewData["MovieId"] = new SelectList(_context.Movies, "Id", "Title", movie.Title);
                return View(movie);
            }


            public async Task<IActionResult> Delete(int? id)
            {
                if (id == null)
                {
                    return NotFound();
                }

                var movie = await _context.Movies
                    .FirstOrDefaultAsync(m => m.Id == id);
                if (movie == null)
                {
                    return NotFound();
                }

                ViewData["MovieId"] = new SelectList(_context.Movies, "Id", "Title", movie.Title);
                return View(movie);
            }


            [HttpPost, ActionName("Delete")]
            [ValidateAntiForgeryToken]
            public async Task<IActionResult> DeleteConfirmed(int id)
            {
                var movie = await _context.Movies.FindAsync(id);
                _context.Movies.Remove(movie);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }

            private bool MovieExists(int id)
            {
                return _context.Movies.Any(e => e.Id == id);
            }
        }
    }
