using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using JamesBondMovieDatabase.Models;

namespace JamesBondMovieDatabase.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }
        //returns view for the index controller
        public IActionResult Index()
        {
            return View();
        }

        //returns view for the privacy controller
        public IActionResult Privacy()
        {
            return View();
        }

        //Response cache reduces the work of the server to generate a response
        //Error returned if there is an issue via ErrorViewModel
        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
