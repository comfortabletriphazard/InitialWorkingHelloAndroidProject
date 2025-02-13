using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using MvcMovie.Data;
using System;
using System.Linq;

namespace MvcMovie.Models
{
    public static class SeedData
    {
        public static void Initialize(IServiceProvider serviceProvider)
        {
            using (var context = new MvcMovieContext(
                serviceProvider.GetRequiredService<
                    DbContextOptions<MvcMovieContext>>()))
            {
                // Look for any movies.
                if (context.Movies.Any())
                {
                    return;   // DB has been seeded
                }

                context.Movies.AddRange(

                new Movie
                {
                    Title = "Casino Royale",
                    Year = DateTime.Parse("2006-11-16"),
                    Director = "Martin Campbell",
                    Synopsis = "James Bond (Daniel Craig) embarks on a " +
                    "mission to prevent Le Chiffre, a mob banker, from " +
                    "winning a high stakes poker game.",
                    Duration = 145,
                    Cast = "Daniel Craig, Mads Mikkelsen, Eva Green, Judi Dench",
                    Budget = 150,
                    Photo = "https://static.wikia.nocookie.net/jamesbond/images/d/df/Casino_Royale_poster.jpeg/revision/latest?cb=20120329172248"
                },

                    new Movie
                    {
                        Title = "GoldenEye",
                        Year = DateTime.Parse("1995-11-21"),
                        Director = "Martin Campbell",
                        Synopsis = "Bond (Pierce Brosnan) travels to Russia " +
                        "to locate the satellite nuclear weapon stolen by " +
                        "Alec, a former agent who Bond believed was dead.",
                        Duration = 130,
                        Cast = "Pierce Brosnan, Izabella Scorupco, Sean Bean, Famke Janssen",
                        Budget = 60,
                        Photo = "https://images-na.ssl-images-amazon.com/images/I/71LfeBfJzJL._AC_SL1130_.jpg"
                    },

                    new Movie
                    {
                        Title = "License To Kill",
                        Year = DateTime.Parse("1989-6-13"),
                        Director = "John Glen",
                        Synopsis = "After Bond's (Timothy Dalton) friend, " +
                        "Felix Leiter, is gravely injured by a drug lord, " +
                        "James Bond seeks revenge. With the MI6 refusing to " +
                        "back him, Bond takes matters into his own hands.",
                        Duration = 133,
                        Cast = "Timothy Dalton, Robert Davi, Carey Lowell",
                        Budget = 32,
                        Photo = "https://www.scope.dk/shared/4/865/danjaq-productions-licence-to-kill_400x600c.jpg"
                    },

                    new Movie
                    {
                        Title = "A View To A Kill",
                        Year = DateTime.Parse("1985-6-13"),
                        Director = "John Glen",
                        Synopsis = "Max Zorin, a menacing microchip " +
                        "manufacturer, develops a scheme to exterminate all " +
                        "of his Silicon Valley competitors. " +
                        "Bond (Roger Moore) must stop him.",
                        Duration = 131,
                        Cast = "Roger Moore, Grace Jones, Christopher Walken, Tanya Roberts",
                        Budget = 30,
                        Photo = "https://blackinkcovers.files.wordpress.com/2019/03/john-barry-a-view-to-a-kill.jpg?strip=info&w=1400"
                    },

                    new Movie
                    {
                        Title = "On Her Majesty's Secret Service",
                        Year = DateTime.Parse("1969-12-18"),
                        Director = "Peter R. Hunt",
                        Synopsis = "James Bond's (George Lazenby) mission is " +
                        "to defeat Blofeld, a man who hypnotizes women to " +
                        "serve his evil.",
                        Duration = 142,
                        Cast = "George Lazenby, Diana Rigg, Telly Savalas",
                        Budget = 7,
                        Photo = "http://jamesbondradio.com/wp-content/uploads/2016/02/On-Her-Majestys-Secret-Service-683x1024.jpg"
                    },

                    new Movie
                    {
                        Title = "Goldfinger",
                        Year = DateTime.Parse("1964-9-17"),
                        Director = "Guy Hamilton",
                        Synopsis = "James Bond (Sean Connery) is tasked with" +
                        " disrupting Auric Goldfinger, a businessman who " +
                        "runs a gold-smuggling ring, as he plans to attack " +
                        "Fort Knox's gold reserves.",
                        Duration = 110,
                        Cast = "Sean Connery, Honor Blackman, Shirley Eaton, Gert Fröbe",
                        Budget = 3,
                        Photo = "https://movieposters2.com/images/1393593-b.jpg"
                    }
                ) ;
                context.SaveChanges();
            }
        }
    }
}