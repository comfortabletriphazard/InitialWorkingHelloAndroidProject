using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Microsoft.AspNetCore.Mvc;
using JamesBondMovieDatabase.Models;

namespace JamesBondMovieDatabase.Controllers
{
    public class MovieReviewController : Controller
    {

        public IActionResult Index()
        {
            List<TableMovieTitle> title = new List<TableMovieTitle>
            {
                new TableMovieTitle()
                {
                    Id = 1,
                    Title = "Casino Royale",

                },

                new TableMovieTitle()
                {
                    Id = 2,
                    Title = "GoldenEye"
                },

                new TableMovieTitle()
                {
                    Id = 3,
                    Title = "Licence to Kill"
                },

                new TableMovieTitle()
                {
                    Id = 4,
                    Title = "A View to a Kill"
                },

                new TableMovieTitle()
                {
                    Id = 5,
                    Title = "On Her Majesty's Secret Service"
                },

                new TableMovieTitle()
                {
                    Id = 6,
                    Title = "Goldfinger"
                }
            };
            ViewBag.title = title;
            return View();
        }
    }
}
