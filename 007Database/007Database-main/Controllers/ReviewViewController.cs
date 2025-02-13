using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using JamesBondMovieDatabase.Models;

public ActionResult Review(int id)
{
    var review = new ReviewViewTable { Title = "Review " + id };
    return View(review);
}